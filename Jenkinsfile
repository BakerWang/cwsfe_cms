 node {
    stage('Build and Test') {
        sh './gradlew war'
        sh './gradlew createTomcatWar'
    }
    //stage('SonarQube analysis') {
        // requires SonarQube Scanner 2.8+
        //def scannerHome = tool 'SonarQube Scanner 2.8';
        //withSonarQubeEnv('CWSFE_CMS_SONAR') {
        //            sh "${scannerHome}/bin/sonar-scanner"
        //}
        //withSonarQubeEnv('CWSFE_CMS_SONAR') {
            // requires SonarQube Scanner for Gradle 2.1+
          //  sh './gradlew sonarqube'
        //}
    //
    //}
    stage('Archive results') {
        archiveArtifacts artifacts: '**/build/libs/*.jar,**/build/libs/*.war', fingerprint: true
    }
 }
