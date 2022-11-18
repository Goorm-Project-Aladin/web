pipeline {
  agent any
  stages {
    stage('Git scm update') {
      steps {
        git url: 'https://github.com/Goorm-Project-Aladin/web.git', branch: 'main'
      }
    }
    stage('Docker build and push') {
      steps {
        sh '''
        chmod 700 gradlew
        ./gradlew build
        '''
      }
    }
    // stage('Clean up git file and docker image') {
    //   steps {
    //     sh '''
    //     rm -rf ./web
    //     docker rmi $(docker images -f "dangling=true" -q)
    //     '''
    //   }
    // }
  }
}