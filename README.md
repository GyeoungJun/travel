# Cloud Native Final 프로젝트 시연 관련

## 클라우드 네이티브 아키텍처(IaaS)
![Untitled](https://github.com/GyeoungJun/travel/assets/110224872/fcf3977d-c0a8-4787-ad31-ab8cd67e6376)

## 클라우드 네이티브 모델링(Biz.)
![image](https://github.com/GyeoungJun/travel/assets/110224872/1979e641-54c6-45bd-98d1-d6300c48028b)
![image](https://github.com/GyeoungJun/travel/assets/110224872/b5329f7d-b776-4f2d-83e1-6275101a42b7)

## 클라우드 네이티브 개발 MSA(Dev.)

### 분산 트랜잭션 - Saga

### 보상처리 - Compensation

### 단일 진입점 - Gateway
- Gateway를 사용하여 마이크로 서비스들의 엔드포인트 단일화

- ateway 를 사용하여 마이크로 서비스들의 엔드포인트 단일화

```java
server:
  port: 8088

---

spring:
  profiles: default
  cloud:
    gateway:
#<<< API Gateway / Routes
      routes:
        - id: flight
          uri: http://localhost:8082
          predicates:
            - Path=/flights/**, 
        - id: flightreservation
          uri: http://localhost:8083
          predicates:
            - Path=/flightReservations/**, 
        - id: payment
          uri: http://localhost:8084
          predicates:
            - Path=/payments/**, 
        - id: lodging
          uri: http://localhost:8085
          predicates:
            - Path=/lodgings/**, 
        - id: lodgingreservation
          uri: http://localhost:8086
          predicates:
            - Path=/lodgingReservations/**, 
        - id: mypage
          uri: http://localhost:8087
          predicates:
            - Path=, 
        - id: frontend
          uri: http://localhost:8080
          predicates:
            - Path=/**
```

### 분산 데이터 프로텍션 - CQRS

- MyFlightInfo
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/8132fd37-5ad5-4550-a491-2e6ea8822ca2)
- MyLodgingInfo
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/32b93c5c-72f3-45a2-b6c8-68cd8029c93c)

## 클라우드 네이티브 운영

### 클라우트 배포 - Container 운영

- ECR 생성
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/a224e185-89da-4d75-b84d-8b65c92d9182)
- CodeBuild 생성
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/2c01c910-1627-428a-8236-1b22aefcc2c2)
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/c843eb9c-0aee-4bd2-be4e-608e2ba4a4a8)
- Buildspec.yml 실행
  - buildspec.yml 실행
    
    ```java
    version: 0.2
    
    env:
      variables:
        IMAGE_REPO_NAME: "user01-travel-flight"
    
    phases:
      install:
        runtime-versions:
          java: corretto11
          docker: 20
      pre_build:
        commands:
          - echo Logging in to Amazon ECR...
          - echo $IMAGE_REPO_NAME
          - echo $AWS_ACCOUNT_ID
          - echo $AWS_DEFAULT_REGION
          - echo $CODEBUILD_RESOLVED_SOURCE_VERSION
          - echo start command
          - $(aws ecr get-login --no-include-email --region $AWS_DEFAULT_REGION)
      build:
        commands:
          - echo Build started on `date`
          - echo Building the Docker image...
          - mvn package -B -Dmaven.test.skip=true
          - docker build -t $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME:$CODEBUILD_RESOLVED_SOURCE_VERSION  .
      post_build:
        commands:
          - echo Build completed on `date`
          - echo Pushing the Docker image...
          - docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME:$CODEBUILD_RESOLVED_SOURCE_VERSION
    
    cache:
      paths:
        - '/root/.m2/**/*'
    ```
- CodeBuild 실행 시 프로젝트 Build 이후 ECR Push
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/9f503fc8-505c-4637-9920-67feb99c53d7)
- ECR에 해당 프로젝트 이미지가 Push된 모습
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/17bbdfcd-0361-4614-8c0a-150671ad5a80)

### 컨테이너 자동확장 - HPA
- “cpu-percent=50 : Pod 들의 요청 대비 평균 CPU 사용율(YAML Spec.에서 요청량이 200 milli-cores일때, 모든 Pod의 평균 CPU 사용율이 100 milli-cores(50%)를 넘게되면 HPA 발생)”
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/85430677-b90a-4789-bca9-4790797d3b72)

### 컨테이너로부터 환경분리 - ConfigMap/Secret
- config-dev.yaml 생성
  ```java
  apiVersion: v1
  kind: ConfigMap
  metadata:
    name: config-dev
    namespace: default
  data:
    FLIGHT_NAME: kimgyeongjun
    FLIGHT_LOG_LEVEL: INFO
  ```
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/fff0f812-6fd3-4ced-8841-3659398e760e)

- configMap의 내용 출력
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/29965078-2735-468c-b73c-027ee2a24b48)

- 실제 프로젝트 내 ConfigMap 설정 값 적용
  - application.yml
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/696b044d-ff01-49b7-87db-40768ce2c4b8)
  - Deployment.yaml
    ```java
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: flight
      labels:
        app: flight
    spec:
      replicas: 1
      selector:
        matchLabels:
          app: flight
      template:
        metadata:
          labels:
            app: flight
        spec:
          containers:
            - name: flight
              image: "kimgyeongjun/flight:0702"
              ports:
                - containerPort: 8080
              env:
              - name: FLIGHT_NAME
                valueFrom:
                  configMapKeyRef:
                    name: config-dev
                    key: FLIGHT_NAME
              - name: FLIGHT_LOG_LEVEL
                valueFrom:
                  configMapKeyRef:
                    name: config-dev
                    key: FLIGHT_LOG_LEVEL
    ```

- 실제 배포 시 Pod 내 ConfigMap 설정 결과 확인(logging level : INFO)
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/903a339d-8628-4589-8b74-2f18910b3eb2)

### 클라우드스토리지 활용 -PVC

- EFS 파일 시스템 생성
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/6e2559cd-40b8-40e0-a4b3-5f2987a2e62f)

- Kubernetes 서비스 계정(SA)과 IAM 역할 연결
  - 환경 변수 설정
    ```java
    export AWS_ROOT_UID=879772956301
    export REGION=ap-northeast-2
    export CLUSTER_NAME=user01-eks
    export FILE_SYSTEM_ID=fs-01fe6e8bb4598a977
    export MY_IMAGE_REPO=602401143452.dkr.ecr.ap-northeast-2.amazonaws.com
    ```
  - Kubernetes에 SA 생성, IAM 정책과 연결
    ```java
    eksctl create iamserviceaccount \
    --override-existing-serviceaccounts \
    --region $REGION \
    --name efs-csi-controller-sa \
    --namespace kube-system \
    --cluster $CLUSTER_NAME \
    --attach-policy-arn arn:aws:iam::$AWS_ROOT_UID:policy/EFSCSIControllerIAMPolicy \
    --approve 
    ```
  - IMAGE_REPO 참조
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/bbb12794-04bf-4e8d-a25f-a121c0ec185f)
  - Cluster에 EFS CSI 드라이버 설치
    ```java
    helm repo add aws-efs-csi-driver https://kubernetes-sigs.github.io/aws-efs-csi-driver
    helm repo update aws-efs-csi-driver
    helm upgrade --install aws-efs-csi-driver aws-efs-csi-driver/aws-efs-csi-driver \
      --namespace kube-system \
      --set image.repository=602401143452.dkr.ecr.ap-northeast-2.amazonaws.com/eks/aws-efs-csi-driver \
      --set controller.serviceAccount.create=false \
      --set controller.serviceAccount.name=efs-csi-controller-sa
    ```
  - EFS csi Driver로 StorageClass 생성
    ```java
    kubectl apply -f - <<EOF
    kind: StorageClass
    apiVersion: storage.k8s.io/v1
    metadata:
      name: efs-sc
    provisioner: efs.csi.aws.com
    parameters:
      provisioningMode: efs-ap
      fileSystemId: $FILE_SYSTEM_ID
      directoryPerms: "700"
    EOF
    ```
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/32fa35f6-6d56-4852-b0fd-fee8a5f04b88)
  - PVC 생성
    - flight-pvc.yaml
      ```java
      kubectl apply -f - <<EOF
      apiVersion: v1
      kind: PersistentVolumeClaim
      metadata:
        name: flight-pvc
        labels:
          app: flight
      spec:
        accessModes:
        - ReadWriteMany
        resources:
          requests:
            storage: 500Mi
        storageClassName: efs-sc
      EOF
      ```
  - 생성된 PVC Bound 확인
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/61da7478-b4e3-4023-b479-8fb4e0b27288)
  - Deployment에 해당 PVC 연결 후 배포
        - Deployment.yaml
      ```java
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: flight
        labels:
          app: flight
      spec:
        replicas: 1
        selector:
          matchLabels:
            app: flight
        template:
          metadata:
            labels:
              app: flight
          spec:
            containers:
              - name: flight
                image: "kimgyeongjun/flight:0702"
                ports:
                  - containerPort: 8080
                resources:
                  requests:
                    cpu: "200m" 
                readinessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 10
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 10
                livenessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 120
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 5
                volumeMounts:
                - mountPath: "/mnt/data"
                  name: volume
            volumes:
            - name: volume
              persistentVolumeClaim:
                claimName: flight-pvc  
      ```
    - Pod에 접속하여 Mount 된 위치에 txt 파일 생성
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/bb29f65d-6f24-4513-bc6a-eadf8ebfbcba)
    - Pod 재배포 이후 txt 파일 새로 생성 / 기존 생성된 txt 파일 확인
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/985cd973-46aa-4e23-844d-170090ecc7bf)


