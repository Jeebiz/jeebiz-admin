create or replace function getMD5 ( vin_string IN VARCHAR2 )
 RETURN VARCHAR2 IS
   temp varchar2(40);
 BEGIN
 temp := dbms_obfuscation_toolkit.MD5(input => utl_raw.cast_to_raw(vin_string));
 RETURN '{MD5}'||utl_raw.cast_to_varchar2(utl_encode.base64_encode(temp));
 END getMD5;
/

create or replace TYPE en_concat_im
AUTHid CURRENT_USER AS OBJECT
(
  CURR_STR VARCHAR2(32767),
  STATIC FUNCTION ODCIAGGREGATEINITIALIZE(SCTX IN OUT en_concat_im) RETURN NUMBER,
  MEMBER FUNCTION ODCIAGGREGATEITERATE(SELF IN OUT en_concat_im,
        P1 IN VARCHAR2) RETURN NUMBER,
  MEMBER FUNCTION ODCIAGGREGATETERMINATE(SELF IN en_concat_im,
              RETURNVALUE OUT VARCHAR2,
              FLAGS IN NUMBER)
        RETURN NUMBER,
  MEMBER FUNCTION ODCIAGGREGATEMERGE(SELF IN OUT en_concat_im,
       SCTX2 IN  en_concat_im) RETURN NUMBER
);
/

create or replace TYPE BODY en_concat_im
  IS
    STATIC FUNCTION ODCIAGGREGATEINITIALIZE(SCTX IN OUT en_concat_im)
    RETURN NUMBER
    IS
    BEGIN
     SCTX := en_concat_im(NULL) ;
     RETURN ODCICONST.SUCCESS;
    END;
    MEMBER FUNCTION ODCIAGGREGATEITERATE(SELF IN OUT en_concat_im,
           P1 IN VARCHAR2)
    RETURN NUMBER
    IS
    BEGIN
     IF(CURR_STR IS NOT NULL) THEN
       CURR_STR := CURR_STR || ',' || P1;
     ELSE
       CURR_STR := P1;
     END IF;
     RETURN ODCICONST.SUCCESS;
    END;
    MEMBER FUNCTION ODCIAGGREGATETERMINATE(SELF IN en_concat_im,
                RETURNVALUE OUT VARCHAR2,
                FLAGS IN NUMBER)
     RETURN NUMBER
    IS
    BEGIN
     RETURNVALUE := CURR_STR ;
     RETURN ODCICONST.SUCCESS;
    END;
    MEMBER FUNCTION ODCIAGGREGATEMERGE(SELF IN OUT en_concat_im,
            SCTX2 IN en_concat_im)
    RETURN NUMBER
    IS
    BEGIN
     IF(SCTX2.CURR_STR IS NOT NULL) THEN
       SELF.CURR_STR := SELF.CURR_STR || ',' || SCTX2.CURR_STR ;
     END IF;
     RETURN ODCICONST.SUCCESS;
    END;
  END;
/

create or replace FUNCTION wm_concat(P2 VARCHAR2) RETURN VARCHAR2 AGGREGATE USING en_concat_im;
/