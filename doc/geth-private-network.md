http://blog.csdn.net/diandianxiyu_geek/article/details/78082551?utm_source=gold_browser_extension
./geth1.7.2 --datadir data init genesis.json

./geth1.7.2 --datadir data  --rpc --rpcaddr 0.0.0.0  console

eth.sendTransaction({from:eth.coinbase,to:"0x1bb882be789739d2cfb3f43c27c75ffa79e3afd4",value:web3.toWei(3,"ether")})

systemctl disable lightdm.service
sudo service lightdm start