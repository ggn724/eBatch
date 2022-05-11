#! /bin/bash
# cnt=`ps -elf | grep GftImgDelete | wc -l`
cnt=`ps -ef | grep -v grep | grep GftImgDelete | wc -l`
date=$(date "+%Y-%m-%d_%H:%M:%S")

echo "$date : GftImgDelete Process count $cnt" >> /app/webtob/eBatch/logs/eBatch_sh.log

if [ $cnt -lt 1 ]; then
    echo "$date : GftImgDelete Process alive" >> /app/webtob/eBatch/logs/eBatch_sh.log

    /app/webtob/eBatch/sh/GftImgDelete.sh
    wait
elif [ $cnt -gt 1 ]; then
    echo "$date : GftImgDelete Process live" >> /app/webtob/eBatch/logs/eBatch_sh.log

    PROCESS=$(ps -elf | grep GftImgDelete | awk '{print $4}')
	echo PROCESS
    if [ "$PROCESS" != "" ]
    then
        echo "$date : GftImgDelete Process($PROCESS) kill." >> /app/webtob/eBatch/logs/eBatch_sh.log
        kill -9 $PROCESS
        wait
    fi
else
    echo "$date : GftImgDelete Process live" >> /app/webtob/eBatch/logs/eBatch_sh.log
fi
