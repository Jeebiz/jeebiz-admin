#FROM reg.hiwepy.com/base-image/openjdk-pinpont:1
#FROM reg.hiwepy.com/base-image/openjdk-shanghai:8
FROM openjdk:8
VOLUME /tmp
ADD ./target/jeebiz-admin-shadow-1.0.0-SNAPSHOT.jar /
ADD ./libs/linux64/libarcsoft_face_engine_jni.so /lib/
ADD ./libs/linux64/libarcsoft_face_engine.so /lib/
ADD ./libs/linux64/libarcsoft_face.so /lib/
RUN chmod 755 /lib/libarcsoft_face_engine_jni.so
RUN chmod 755 /lib/libarcsoft_face_engine.so
RUN chmod 755 /lib/libarcsoft_face.so
# EXPOSE 8087

#ENTRYPOINT ["java", "-XX:InitiatingHeapOccupancyPercent=60", "-XX:MinHeapDeltaBytes=524288", "-XX:MetaspaceSize=128m", "-XX:MaxMetaspaceSize=256m", "-XX:InitialHeapSize=512M", "-XX:MaxHeapSize=512M", "-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps", "-XX:+UseG1GC",  "-XX:GCLogFileSize=20m",  "-XX:+HeapDumpOnOutOfMemoryError",  "-XX:HeapDumpPath=/logs/heaperror.log",  "-Xloggc:/logs/gcerror.log", "-javaagent:/agent/pinpoint-bootstrap-1.7.1.jar", "-jar", "jeebiz-admin-shadow-1.0.0-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-XX:InitiatingHeapOccupancyPercent=60", "-XX:MinHeapDeltaBytes=262144", "-XX:MetaspaceSize=128m", "-XX:MaxMetaspaceSize=256m", "-XX:InitialHeapSize=256M", "-XX:MaxHeapSize=256M", "-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps", "-XX:+UseG1GC",  "-XX:GCLogFileSize=20m",  "-XX:+HeapDumpOnOutOfMemoryError",  "-XX:HeapDumpPath=/logs/heaperror.log",  "-Xloggc:/logs/gcerror.log", "-jar", "jeebiz-admin-shadow-1.0.0-SNAPSHOT.jar"]