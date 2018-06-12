# activity-recognition-transition
<img src="./res/Jetpack_logo.png" width="200">

이제부터 안드로이드에서 사용자가 어떤 활동을 하고 있는지 쉽게 알 수 있습니다!!!

Google의 **Activity Recognition Transition API**를 통해 새로운 기능을 구현해 봅시다.

## [Activity Recognition Transition API 란?]
사용자의 활동 상태를 인식해주는 API입니다. 엄밀히 말하면 핸드폰(디바이스)의 활동 상태라고 할 수 있습니다. 사용자가 핸드폰과 같이 있다는 전제하에 사용자의 활동 상태를 인식한다는 것입니다.

<sup>[1]</sup>[이 곳](https://developers.google.com/android/reference/com/google/android/gms/location/DetectedActivity)에 따르면 사용자의 활동 상태를 6가지로 분류시켜 앱에 알릴 수 있습니다. 

```
IN_VEHICLE : 디바이스가 차량을 이용하는 사용자와 같이 있는지 알 수 있습니다.
ON_BICYCLE : 디바이스가 자전거를 이용하는 사용자와 같이 있는지 알 수 있습니다.
ON_FOOT    : 디바이스가 사용자와 같이 있는지 알 수 있습니다.
RUNNING    : 디바이스가 뛰고 있는 사용자와 같이 있는지 알 수 있습니다. 
WALKING    : 디바이스가 걷고 있는 사용자와 같이 있는지 알 수 있습니다.
STILL      : 디바이스가 움직이지 않고 한 곳에 머물러 있습니다.
```

## [개발 배경]
기존에 유사한 기능을 구현하기 위해 개발자는 사용자의 위치, 센서 데이터와 같은 다양한 신호를 결합하는데 기회비용이 컸습니다.
설사 기능을 구현했다 하더라도 사용자의 활동 변화를 지속적으로 확인하며 배터리 수명을 단축시키곤 했습니다.
구글은 이러한 이유로 모든 처리를 간단하게 할 수 있는 API 제공하기 시작했습니다.

## [구현 하기]
<sup>[2]</sup>[Activity Recognition Transition API](https://codelabs.developers.google.com/codelabs/activity-recognition-transition/#0) 클릭

## [사용 후기]
현재 테스트 앱을 제작하였습니다.

인식률에 대해 테스트 후 업데이트 하도록 하겠습니다.


## [참조 문서]

[1] DetectedActivity : https://developers.google.com/android/reference/com/google/android/gms/location/DetectedActivity

[2] Google codelab : https://codelabs.developers.google.com/codelabs/activity-recognition-transition/#0
