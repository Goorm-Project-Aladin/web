pipeline {
  agent any
  stages {
    stage('git scm update') {
      steps {
        git url: 'https://github.com/Goorm-Project-Aladin/web.git', branch: 'main'
      }
    }
    stage('docker build and push') {
      steps {
        sh '''
        aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 535954374321.dkr.ecr.ap-northeast-2.amazonaws.com
        docker build -t web .
        docker tag aladin:latest 535954374321.dkr.ecr.ap-northeast-2.amazonaws.com/aladin:latest
        docker push 535954374321.dkr.ecr.ap-northeast-2.amazonaws.com/aladin:latest
        docker rmi $(docker images -f "dangling=true" -q)
        '''
      }
    }
  }
}