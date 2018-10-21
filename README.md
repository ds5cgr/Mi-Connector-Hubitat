# Mi-Connector-Hubitat
Connector for Xiaomi Devices with [Hubitat](https://hubitat.com/)

Simplify setup process for xiaomi devices to Hubitat.<br/>
If Mi-Connector is installed, virtual devices is registered automatically by Mi Connector App.<br/>
You don't have to do anything to add xiaomi device in Hubitat IDE.

<br/><br/>
## Donation
If this project help you, you can give me a cup of coffee<br/>
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://paypal.me/fison67)
<br/><br/>

#### Example Video<br/>
[![Example](https://img.youtube.com/vi/CtPce-KBVcY/0.jpg)](https://www.youtube.com/watch?v=CtPce-KBVcY)

<br/><br/>
### Latest Version
#### - fison67/mi-connector:latest
#### - fison67/mi-connector-arm:latest
### Beta Version
#### - fison67/mi-connector:test
#### - fison67/mi-connector-arm:test
<br/><br/>

## History


<br/><br/>
## Management Web Desktop Version.<br/>
![web-dashboard-total](https://github.com/fison67/mi_connector/blob/master/imgs/web-dashboard-total.png) 


<br/><br/>

## Management Web Mobile Version.<br/>
![total](https://github.com/fison67/mi_connector/blob/master/imgs/total.png) 


<br/><br/>
## DTH Example<br/>
<a href="https://github.com/fison67/mi_connector/blob/master/imgs/dth/README.md">![total2](https://github.com/fison67/mi_connector/blob/master/imgs/dth/xiaomi-dth-exam.png) 


<br/><br/>

# Install
#### Preparing
```
You need a Raspbery pi or Synology Nas to install Mi Connector API Server
```
<br/><br/>

## Install API Server<br/>
#### Raspberry pi<br/>
> You must install docker first.
```
sudo mkdir /docker
sudo mkdir /docker/mi-connector
sudo chown -R pi:pi /docker
docker pull fison67/mi-connector-arm:latest
docker run -d --restart=always -v /docker/mi-connector:/config --name=mi-connector-arm --net=host fison67/mi-connector-arm:latest
```

###### Synology nas<br/>
> You must install docker first.<br/>
[See the Manual](doc/install/synology/README.md) file for details<br/>
Current Beta version is 'mi-connector:test'
```
make folder /docker/mi-connector
Run Docker
-> Registery 
-> Search fison67/mi-connector
-> Advanced Settings
-> Volume tab -> folder -> Select mi-connector & Mount path '/config'
-> Network tab -> Check 'use same network as Docker Host'
-> Complete
```

###### Linux x86 x64<br/>
> You must install docker first.
```
sudo mkdir /docker
sudo mkdir /docker/mi-connector
docker pull fison67/mi-connector:latest
docker run -d --restart=always -v /docker/mi-connector:/config --name=mi-connector --net=host fison67/mi-connector:latest
```

<br/><br/>
 
## Install Driver<br/>
```
Go to the Hubitat IDE
Click Drivers Code
Click New Driver
Copy content of file in the devicetypes/fison67 folder to the area
Click Create
Loop until all of file is registered
```
<br/><br/>

## Install App<br/>
See the [Manual](doc/install/smartapp/README.md) file for details
```
Connect to the Hubitat IDE
Click Apps Code
Click New App
Click From Code 
Copy content of mi_connector.groovy & Paste
Click Create
Enable OAuth
Update Click
```

<br/><br/>


## Install DB<br/>
#### Raspberry pi<br/>
> You must install docker first.
```
docker pull jsurf/rpi-mariadb
docker run -d --name mariadb -e MYSQL_ROOT_PASSWORD=password1234 -e TZ=Asia/Seoul -p 33006:3306 -d jsurf/rpi-mariadb
```
###### Synology nas<br/>
> You must install docker first.<br/>
Run Docker
-> Registery 
-> Search mariadb
-> Advanced Settings
-> Port setup tab -> local port 33006, container post 3306
-> Enviroment tab -> MYSQL_ROOT_PASSWORD (password1234),  TZ (Asia/Seoul)
-> Complete

#### Linux x86 x64<br/>
> You must install docker first.
```
docker pull mariadb
docker run -d --name mariadb -e MYSQL_ROOT_PASSWORD=password1234 -e TZ=Asia/Seoul -p 33006:3306 -d mariadb
```

<br/>
Fill the blank [db_url, db_port, db_password] on the Mi-connector web menu setup
If you don't change value, it must be a [ localhost, 33006, password1234 ].
Restart a Mi-connector container.
<br/><br/>

## Problem solving
#### Suddenly Mi connector can't connect to xiaomi devices
> If ip address and token are correct but not connected, remove the device on MiHome<br/>
And add the device again on MiHome. Then token is regenerated. Add a device on Mi-Connector again.
<br/><br/>

#### Some of xiaomi product is not registered
> Some of product is not getting token automatically like Xiaomi Vacuum. You have to get token yourself.<br/>
And go to the 'Manage Device' > 'Device List' >  Click the add button > Fill out the blank. (IP & Token) > Click OK Button
<br/><br/>

#### When Zigbee devices is not working(No response)
> a. Open up the MiHome App<br/>
b. From the Profile > Settings screen set to the Location field to “Mainland China”<br/>
c. Open up the device detail screen for the Gateway device<br/>
d. Click the triple dots button in the top-right corner of the screen to open the More screen<br/>
e. Open the About screen<br/>
f. At the bottom of the screen keep tapping the version number until a notice appears<br/>
g. From the same screen open the Local Area Network Communication Protocol screen<br/>
h. Slide the toggle element for “Local Area Network Communication Protocol” to enabled<br/>
<img src="https://www.domoticz.com/wiki/images/4/41/Mihome_lan.png"><br/>
i. Restart Mi-Connector and then register gateway.<br/>

#### When you can't control device on ST
> Maybe you put the server address on Smart app(Mi-Connector)<br/>
If it's wrong server address and device is added already, just stop docker and remove all devices which was added by mi-connector.<br/>
Even if you change the address, the server address of each device is not updated. So you must remove devices.<br/>
And put the right server address.(ex. 192.168.1.22:30000), restart docker.<br/>
When you control device on ST, if you can see the following the log it means that communication is working well with ST.<br/>
2018-09-17 03:14:14 [info]: Requested to control by ST >> [zhimi.airpurifier.m1] >> {"id":"54532856","cmd":"power","data":"off"}<br/>
If you can't see anything, it's not working well..

#### How to get token
> https://www.home-assistant.io/components/vacuum.xiaomi_miio/#retrieving-the-access-token

## These devices is not working auto mode.
### You must add device manually
#### Management Web -> Manage Device -> Device List -> Fill the address & token -> Add Button
- Yeelight Desk Lamp
- Yeelight Color Bulb
- Yeelight White Bulb
- Yeelight LED Strip
- Mi Robot Vacuum
- Mi Air Quality Monitor (PM2.5)
- Mi Smart Power Strip 1
- Mi Smart Power Strip 2

## Support devices<br/>
#### Wi-Fi Version
| Type  | Model | Tested | Beta |
| ------------- | ------------- | ------------- | ------------- |
| Xiaomi Air Purifier  | zhimi.airpurifier.m1  |   O |    |
|   | zhimi.airpurifier.v1  |   X |    |
|   | zhimi.airpurifier.v2  |   X |    |
|   | zhimi.airpurifier.v3  |   X |    |
|   | zhimi.airpurifier.v6  |   X |    |
|   | zhimi.airpurifier.ma2  |   X |    |
| Xiaomi Humidifier  | zhimi.humidifier.v1  |   X |    |
|   | zhimi.humidifier.ca1  |   O |    |
| Xiaomi Vacuum  | rockrobo.vacuum.v1  |   O |    |
|   | roborock.vacuum.s5  |   X |    |
| Xiaomi Power Socket  | chuangmi.plug.v1  |   X |    |
|   | chuangmi.plug.v2  |   X |    |
|   | chuangmi.plug.m1  |   O |    |
| Xiaomi Power Strip  | qmi.powerstrip.v1  |   X |    |
|   | zimi.powerstrip.v2  |   O |    |
| Xiaomi Air Monitor  | zhimi.airmonitor.v1  |   O |    |
| Xiaomi Gateway  | lumi.gateway.v2  |   X |    |
|   | lumi.gateway.v3  |   O |    |
| Xiaomi Fan  | zhimi.fan.v2  |   O |    |
|   | zhimi.fan.v3  |   O |    |
|   | zhimi.fan.sa1  |   X |  O |
|   | zhimi.fan.za1  |   O |  O |
| Yeelight Mono  | yeelink.light.lamp1  |   X |    |
|   | yeelink.light.mono1  |   O |    |
|   | yeelink.light.ct2  |   O |    |
| Yeelight Color  | yeelink.light.color1  |   O |    |
|   | yeelink.light.color2  |   O |    |
|   | yeelink.light.strip1  |   O |    |
|  Yeelight Ceiling | yeelink.light.ceiling1  |   O |    |
|  Philips Ceiling | philips.light.ceiling  |   O |    |
|  Xiaomi IR Remote | chuangmi.ir.v2  |   △ |  O |



#### Zigbee Version
| Type  | Model | Tested | Beta |
| ------------- | ------------- | ------------- | ------------- |
| Xiaomi Motion Sensor | lumi.motion  |  X  |    |
|  | lumi.motion.aq2  |  O  |    |
| Xiaomi Door/Window Sensor | lumi.magnet  |  X  |    |
|  | lumi.magnet.aq2  |  O  |    |
| Xiaomi Weather Sensor | lumi.weather  |  O  |    |
|  | lumi.sensor_ht  |  O  |    |
| Xiaomi Power Socket | lumi.plug  |  O  |    |
| Xiaomi Button | lumi.switch  |  O  |    |
|  | lumi.switch.v2  |  O  |    |
|  | lumi.86sw1  |  O  |    |
|  | lumi.86sw2  |  O  |    |
| Xiaomi Cube | lumi.cube  |  O  |    |
| Xiaomi Wall Switch | lumi.ctrl_neutral1  |  O  |    |
|  | lumi.ctrl_neutral2  |  O  |    |
| Xiaomi Smoke Sensor | lumi.smoke  |  O  |    |
| Xiaomi Gas Sensor | lumi.gas  |  O  |    |
| Xiaomi Water Sensor | lumi.water  |  O  |    |
| Xiaomi Curtain Motor | lumi.curtain  |  O  |    |
| Xiaomi Vibration Sensor | lumi.vibration  |  O  |  O  |

#### Bluetooth Version
| Type  | Model | Tested | Beta |
| ------------- | ------------- | ------------- | ------------- |
| Xiaomi Flora | ble.flora  |  △  |  O  |
| Xiaomi Flora Pot | ble.floraPot  |  △  |  O  |
| Xiaomi Temp-Humid Sensor | ble.mitemperature  |  △  |  O  |


<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/button.jpg" title="Button" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/button_aq.png" title="Button Aqara" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/cube.png" title="Cube" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/door.jpg" title="Door" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/door_aq.png" title="Door Aqara" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/fire.jpg" title="Fire Sensor" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/gateway.jpg" title="Gateway" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/humidifier.jpg" title="Humidifier #1" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/humidifier2.png" title="Humidifier #2" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/motion.jpg" title="Motion Sensor" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/motion_aq.png" title="Motion Aqara Sensor" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/smoke.jpg" title="Smoke Sensor" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/socket.png" title="Power Socket" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/wall_socket_1.png" title="Wall Socket #1" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/wall_socket_2.png" title="Wall Socket #2" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/weather.jpeg" title="Weather Sensor" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/weather_aq.png" title="Weather Aqara Sensor" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/xiaomi_fan.jpg" title="Fan" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/wireless_1.png" title="Button" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/wireless_2.png" title="Button" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/yeelight_color.jpg" title="Yeelight Color" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/yeelight_mono.jpg" title="Yeelight Mono" width="200px"><img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/air_purifier.jpg" title="Air Purifier" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/air-monitor.jpg?raw=true" title="Air Monitor" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/water.jpg?raw=true" title="Water Sensor" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/curtain.png?raw=true" title="Curtain" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/yeelight-color-e27.jpg?raw=true" title="Yeelight Color E27" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/yeelight-mono-e27.png?raw=true" title="Yeelight Mono E27" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/ceiling.jpg?raw=true" title="Ceiling" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/power-strip.png?raw=true" title="Power Strip" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/philips-ceiling.jpg?raw=true" title="Philips Ceiling Light" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/ir-remote.jpg?raw=true" title="IR Remote" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/mi-flora.png?raw=true" title="Flora" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/flora-pot.jpg?raw=true" title="Flora Pot" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/vibration.jpeg?raw=true" title="Vibration" width="200px">
<img src="https://github.com/fison67/mi_connector/blob/master/imgs/product/ble-temp-humid.jpg?raw=true" title="Bluetooth Temperature Humidity Sensor" width="200px">

<br/><br/>
## Library
- https://github.com/aholstenson/miio
- https://github.com/zlargon/google-tts

<br/><br/>
## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

<br/><br/>

