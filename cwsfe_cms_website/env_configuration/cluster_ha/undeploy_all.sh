#!/usr/bin/env bash

JBOSS_HOME=/home/rmo/BinaryPrograms/wildfly-8.2.0.Final
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"domain"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

$JBOSS_CLI -c "undeploy cwsfe.war"
$JBOSS_CLI -c "undeploy cwsfe_cms.war"