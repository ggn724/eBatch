#/bin/sh

batch_nm=GftImgDelete
echo $batch_nm

export path="/app/webtob/eBatch/jar"
${JAVA_HOME}/bin/java -jar ${path}/${batch_nm}.jar
exit 0
