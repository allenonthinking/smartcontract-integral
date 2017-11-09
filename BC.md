stepDuration 设定成5秒产生一个区块
validators 设定Authority的地方

parity --config node0.toml

{"crypto":{"cipher":"aes-128-ctr","ciphertext":"a805311b1b9325d946a514f5b20f3381354abd9299d966a4336634505babbd06","kdf":"scrypt","mac":"68cfb9691c663659c561170fc949d9794970c7042425a9bb3f2b73686a5c8659","cipherparams":{"iv":"cbebdce86410e2749897f94c14ed3e36"},"kdfparams":{"dklen":32,"salt":"b3665321ff3e1eccf8fd65b5a210f1669385f4543920b904b5cacdf5eb415f27","n":262144,"p":1,"r":8}},"id":"d4f43651-9f50-49aa-8900-04d5f2a76fba","version":3,"address":"3d8219f37278287b65752876cef17d142993f20f"}

user
3d8219f37278287b65752876cef17d142993f20f


{"crypto":{"cipher":"aes-128-ctr","ciphertext":"3e5c6a399eb6ef3ac78ae56f89af86514497f5b55232a67cc0fe37601032d18d","kdf":"scrypt","mac":"8c464c451e6183eefefebdc6bb15d5f6749b00c25baea5cac0cbdaf5f55d1f56","cipherparams":{"iv":"ce12a824589e3ec6b28d372377056794"},"kdfparams":{"dklen":32,"salt":"fb84b8196b2ca69b7245a264109f185c2bf9616a07f656cf6b607139f62fb301","n":262144,"p":1,"r":8}},"id":"f56dc753-f334-48b3-870f-c62f748998d4","version":3,"address":"74b361f65421842682eec056b2b1c0c3c18f1794"}

authority0
74b361f65421842682eec056b2b1c0c3c18f1794


{"crypto":{"cipher":"aes-128-ctr","ciphertext":"2da5ba134f1b20f43edb2b54ca607bea0ac542e714b79549e34e4cbae2ec9220","kdf":"scrypt","mac":"2593e9cbe479e69bab3a160981241b2915f3ee265c8b9213b4edf06ac7a2abec","cipherparams":{"iv":"66138de70908cd8b8f69eb2df5a41225"},"kdfparams":{"dklen":32,"salt":"2b6e87bd3dc89d6bc7bc2304d75e35833d965772dfbcfba0d4ee8272fbc3497a","n":262144,"p":1,"r":8}},"id":"50b38b97-3950-48c4-945c-f74027762bda","version":3,"address":"a35b991d906074609f95614c32b95e0e5140257d"}

authority1
a35b991d906074609f95614c32b95e0e5140257d



./geth1.7.2 --datadir data init genesis.json

./geth1.7.2 --datadir data   --rpcaddr 0.0.0.0  console