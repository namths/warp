pipeline {
  agent any
  stages {
    stage("Compile") {
      steps {
        sh "/var/jenkins_home/tools/maven/maven-3.6.3/mvn clean compile"
      }
    }
    stage("Unit test") {
      steps {
        sh "/var/jenkins_home/tools/maven/maven-3.6.3/mvn test"
      }
    }
    stage("Docker build") {
      steps {
        sh "docker build -t thnam/portal-vimc ."
      }
    }
    stage("Deploy to staging") {
      steps {
        sh "docker run -d --rm -p 8088:8080 --name portal-vimc thnam/portal-vimc"
      }
    }
  }
}
