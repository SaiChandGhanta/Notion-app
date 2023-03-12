#!/bin/bash
# Inspired from https://github.com/hhcordero/docker-jmeter-client
# Basically runs jmeter, assuming the PATH is set to point to JMeter bin-dir (see Dockerfile)
#
# This script expects the standdard JMeter command parameters.
#

# Install jmeter plugins available on /plugins volume
if [ -d $JMETER_CUSTOM_PLUGINS_FOLDER ]
then
    for plugin in ${JMETER_CUSTOM_PLUGINS_FOLDER}/*.jar; do
        cp $plugin ${JMETER_HOME}/lib/ext
    done;
fi

# Execute JMeter command
set -e
freeMem=`awk '/MemAvailable/ { print int($2/1024) }' /proc/meminfo`

[[ -z ${JVM_XMN} ]] && JVM_XMN=$(($freeMem/10*2))
[[ -z ${JVM_XMS} ]] && JVM_XMS=$(($freeMem/10*8))
[[ -z ${JVM_XMX} ]] && JVM_XMX=$(($freeMem/10*8))

export JVM_ARGS="-Xmn${JVM_XMN}m -Xms${JVM_XMS}m -Xmx${JVM_XMX}m"

echo "START Running Jmeter on `date`"
echo "JVM_ARGS=${JVM_ARGS}"
echo "jmeter args=$@"


# Keep entrypoint simple: we must pass the standard JMeter arguments
EXTRA_ARGS=-Dlog4j2.formatMsgNoLookups=true
echo "jmeter ALL ARGS=${EXTRA_ARGS} $@"

#removing the folders if they are created
rm -rf reports > /dev/null 2>&1
rm -rf TodoPrePopulate_reports > /dev/null 2>&1
rm -rf jmeter.log > /dev/null 2>&1
rm -rf TodoPrePopulateDb_jmeter.log > /dev/null 2>&1
rm -rf TodoTestPlan.jtl > /dev/null 2>&1
rm -rf TodoPrePopulateDbTestPlan.jtl > /dev/null 2>&1

#prepopulating the db
jmeter -JHost=${host} -n -t TodoPrePopulateDbTestPlan.jmx -l TodoPrePopulateDbTestPlan.jtl -j TodoPrePopulateDb_jmeter.log -e -o TodoPrePopulate_reports/

#load testing the db
jmeter ${EXTRA_ARGS} -JHost=${host} $@

echo "END Running Jmeter on `date`"
