# GameLib
## 마인크래프트 게임 플러그인을 위한 라이브러리

## 구조
GameLib는 서버가 플러그인을 불러오는 방식과 비슷하게, API와 플러그인으로 이루어져 있습니다. 플러그인은 API를 사용하는 애드온을 불러옵니다.

## 기능
게임 아레나(Arena), 게임 큐(ArenaQueue) 기능과 특수 능력을 가진 아이템(ItemAbility)으로 이루어져 있습니다. 

***Arena와 ArenaQueue 기능은 [Hypixel](https://hypixel.net) 에서, 능력 아이템 기능은 [psychics](https://github.com/monun/psychics) 에서 영감을 받았습니다.***

## Sample
- 하이픽셀 UHC와 비슷한 특수 아이템 서바이벌 미니게임
    - 박쥐를 날리는 돌삽
    - 번개를 치는 블레이즈 막대기
    - 플레이어에게 발광효과를 주는 다이아몬드
    - 사망시 아이템이 드랍되지 않고 킵 되는 에메랄드
- 최대 2명이 참가할 수 있는 큐

## 사용 라이브러리
* [InvFX](https://github.com/monun/invfx)