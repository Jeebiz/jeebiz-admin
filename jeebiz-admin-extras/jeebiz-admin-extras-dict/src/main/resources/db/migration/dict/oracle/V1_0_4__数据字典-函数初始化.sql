/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2019/08/01 11:03:29                           */
/*==============================================================*/
/*==============================================================*/
/* TYPE: type_str                                          */
/*==============================================================*/
CREATE OR REPLACE TYPE TYPE_STR IS TABLE OF VARCHAR2(250);
/

/*==============================================================*/
/* TYPE: en_concat_im                                          */
/*==============================================================*/
CREATE OR REPLACE TYPE EN_CONCAT_IM
AUTHID CURRENT_USER AS OBJECT
(
  CURR_STR VARCHAR2(32767),
STATIC FUNCTION ODCIAGGREGATEINITIALIZE(SCTX IN OUT EN_CONCAT_IM)
  RETURN NUMBER,
MEMBER FUNCTION ODCIAGGREGATEITERATE(SELF IN OUT EN_CONCAT_IM,
                                     P1   IN     VARCHAR2)
  RETURN NUMBER,
MEMBER FUNCTION ODCIAGGREGATETERMINATE(SELF        IN  EN_CONCAT_IM,
                                       RETURNVALUE OUT VARCHAR2,
                                       FLAGS       IN  NUMBER)
  RETURN NUMBER,
MEMBER FUNCTION ODCIAGGREGATEMERGE(SELF  IN OUT EN_CONCAT_IM,
                                   SCTX2 IN     EN_CONCAT_IM)
  RETURN NUMBER
);
/

CREATE OR REPLACE TYPE BODY EN_CONCAT_IM
IS
  STATIC FUNCTION ODCIAGGREGATEINITIALIZE(SCTX IN OUT EN_CONCAT_IM)
    RETURN NUMBER
  IS
    BEGIN
      SCTX := en_concat_im(NULL);
      RETURN ODCICONST.SUCCESS;
    END;
  MEMBER FUNCTION ODCIAGGREGATEITERATE(SELF IN OUT EN_CONCAT_IM,
                                       P1   IN     VARCHAR2)
    RETURN NUMBER
  IS
    BEGIN
      IF (CURR_STR IS NOT NULL)
      THEN
        CURR_STR := CURR_STR || ',' || P1;
      ELSE
        CURR_STR := P1;
      END IF;
      RETURN ODCICONST.SUCCESS;
    END;
  MEMBER FUNCTION ODCIAGGREGATETERMINATE(SELF        IN  EN_CONCAT_IM,
                                         RETURNVALUE OUT VARCHAR2,
                                         FLAGS       IN  NUMBER)
    RETURN NUMBER
  IS
    BEGIN
      RETURNVALUE := CURR_STR;
      RETURN ODCICONST.SUCCESS;
    END;
  MEMBER FUNCTION ODCIAGGREGATEMERGE(SELF  IN OUT EN_CONCAT_IM,
                                     SCTX2 IN     EN_CONCAT_IM)
    RETURN NUMBER
  IS
    BEGIN
      IF (SCTX2.CURR_STR IS NOT NULL)
      THEN
        SELF.CURR_STR := SELF.CURR_STR || ',' || SCTX2.CURR_STR;
      END IF;
      RETURN ODCICONST.SUCCESS;
    END;
END;
/

/*==============================================================*/
/* FUNCTION: en_concat   初始化列转行函数                       */
/*==============================================================*/
CREATE OR REPLACE FUNCTION en_concat(P1 VARCHAR2)
  RETURN VARCHAR2 AGGREGATE USING EN_CONCAT_IM;
/

CREATE OR REPLACE FUNCTION split(p_str VARCHAR2, p_delimiter VARCHAR2 DEFAULT ',')
  RETURN TYPE_STR
IS
  rs                TYPE_STR := type_str();
  l_str             VARCHAR2(4000) := '';
  l_len             NUMBER := 0;
  l_delimiter_index NUMBER := 0;
  BEGIN
    l_str := p_str;
    l_len := length(p_delimiter);
    WHILE length(l_str) > 0 LOOP
      l_delimiter_index := instr(l_str, p_delimiter);
      IF l_delimiter_index > 0
      THEN
        rs.extend;
        rs(rs.count) := substr(l_str, 1, l_delimiter_index - 1);
        l_str := substr(l_str, l_delimiter_index + l_len);
      ELSE
        rs.extend;
        rs(rs.count) := l_str;
        EXIT;
      END IF;
    END LOOP;
    RETURN rs;
  END split;
/