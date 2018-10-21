/**
 *  Xiaomi Remote Air Conditioner (v.0.0.1)
 *
 * MIT License
 *
 * Copyright (c) 2018 fison67@nate.com
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
*/
 
import groovy.json.JsonSlurper

metadata {
	definition (name: "Xiaomi Remote Air Conditioner", namespace: "fison67", author: "fison67") {
        capability "Switch"
        capability "Configuration"
        capability "Switch Level"
        
        command "setStatus"
        command "playIRCmdByID", ["string"]
        command "remoteAir1"
        command "remoteAir2"
        command "remoteAir3"
        command "remoteAir4"
        command "remoteAir5"
        command "remoteAir6"
        command "remoteAir7"
        command "remoteAir8"
        command "remoteAir9"
        command "remoteAir10"
        command "remoteAir11"
        command "remoteAir12"
        command "remoteAir13"
        command "remoteAir14"
        command "remoteAir15"
        
        command "setTimeRemaining"
        command "stop"
	}


	simulator {
	}
    
	preferences {
        input name: "syncByDevice", title:"Sync By Device" , type: "bool", required: true, defaultValue:true, description:"" 
	}

	tiles(scale: 2) {
		
        multiAttributeTile(name:"switch", type: "generic", width: 6, height: 2){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'${name}', action:"off",  icon:"https://github.com/fison67/mi_connector/blob/master/icons/virtual-remote-controller.png?raw=true", backgroundColor:"#00a0dc", nextState:"off"
                attributeState "off", label:'${name}', action:"on", icon:"https://github.com/fison67/mi_connector/blob/master/icons/virtual-remote-controller.png?raw=true", backgroundColor:"#ffffff", nextState:"on"
			}
		}
        
        controlTile("level", "device.level", "slider", height: 1, width: 2, range:"(15..30)") {
	    	state "temperature", action:"setLevel"
		}
        
        valueTile("remoteAir1", "device.remoteAir1", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir1"
        }
        valueTile("remoteAir2", "device.remoteAir2", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir2"
        }
        valueTile("remoteAir3", "device.remoteAir3", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir3"
        }
        valueTile("remoteAir4", "device.remoteAir4", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir4"
        }
        valueTile("remoteAir5", "device.remoteAir5", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir5"
        }
        valueTile("remoteAir6", "device.remoteAir6", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir6"
        }
        valueTile("remoteAir7", "device.remoteAir7", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir7"
        }
        valueTile("remoteAir8", "device.remoteAir8", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir8"
        }
        valueTile("remoteAir9", "device.remoteAir9", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir9"
        }
        valueTile("remoteAir10", "device.remoteAir10", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir10"
        }
        valueTile("remoteAir11", "device.remoteAir11", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir11"
        }
        valueTile("remoteAir12", "device.remoteAir12", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir12"
        }
        valueTile("remoteAir13", "device.remoteAir13", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir13"
        }
        valueTile("remoteAir14", "device.remoteAir14", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir14"
        }
        valueTile("remoteAir15", "device.remoteAir15", decoration: "flat", width: 2, height: 1 ) {
            state "default", label:'${currentValue}', action:"remoteAir15"
        }
        
        valueTile("timer_label", "device.leftTime", decoration: "flat", width: 2, height: 1) {
            state "default", label:'Set Timer\n${currentValue}'
        }
        
        controlTile("time", "device.timeRemaining", "slider", height: 1, width: 1, range:"(0..120)") {
	    	state "time", action:"setTimeRemaining"
		}
        
        standardTile("tiemr0", "device.timeRemaining") {
			state "default", label: "OFF", action: "stop", icon:"st.Health & Wellness.health7", backgroundColor:"#c7bbc9"
		}
        
	}
}

def isIRRemoteDevice(){
	return true
}

def msToTime(duration) {
    def seconds = (duration%60).intValue()
    def minutes = ((duration/60).intValue() % 60).intValue()
    def hours = ( (duration/(60*60)).intValue() %24).intValue()

    hours = (hours < 10) ? "0" + hours : hours
    minutes = (minutes < 10) ? "0" + minutes : minutes
    seconds = (seconds < 10) ? "0" + seconds : seconds

    return hours + ":" + minutes + ":" + seconds
}

def stop() { 
	log.debug "stop"
	unschedule()
	state.timerCount = 0
	updateTimer()
}

def timer(){
	if(state.timerCount > 0){
    	state.timerCount = state.timerCount - 30;
        if(state.timerCount <= 0){
        	if(device.currentValue("switch") == "on"){
        		off()
            }
        }else{
        	runIn(30, timer)
        }
        updateTimer()
    }
}

def updateTimer(){
    def timeStr = msToTime(state.timerCount)
    sendEvent(name:"leftTime", value: "${timeStr}")
    sendEvent(name:"timeRemaining", value: Math.round(state.timerCount/60))
}

def processTimer(second){
	if(state.timerCount == null){
    	state.timerCount = second;
    	runIn(30, timer)
    }else if(state.timerCount == 0){
		state.timerCount = second;
    	runIn(30, timer)
    }else{
    	state.timerCount = second
    }
    updateTimer()
}

def setTimeRemaining(time) { 
	if(time > 0){
        log.debug "Set a Timer ${time}Mins"
        processTimer(time * 60)
        setPowerByStatus(true)
    }
}

def setPowerByStatus(turnOn){
	if(device.currentValue("switch") == (turnOn ? "off" : "on")){
        if(turnOn){
        	on()
        }else{
        	off()
        }
    }
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

def setExternalAddress(address){
	log.debug "External Address >> ${address}"
	state.externalAddress = address
}

def setInfo(String app_url, String id) {
	log.debug "${app_url}, ${id}"
	state.app_url = app_url
    state.id = id
}

def setData(dataList){
	for(data in dataList){
    	if(data.temperature != null){
        	state['temperature-' + data.temperature] = data.code
        }else if(data.id != null){
        	state[data.id] = data.code
            if(data.id != 'air-on' && data.id != 'air-off' ){
        		sendEvent(name:"remoteAir" + data.id.substring(11, data.id.length()), value: data.title )
            }
        }
    }
}

def setStatus(data){
	sendEvent(name:"switch", value: data )
}

def on(){
	playIRCmd(state['air-on'])
    if(!syncByDevice){
		sendEvent(name:"switch", value: "on" )
    }
}

def off(){
	playIRCmd(state['air-off'])
    if(!syncByDevice){
		sendEvent(name:"switch", value: "off" )
	}
}

/**
*  mode >> auto, cool, dry, coolClean, dryClean, fanOnly, heat, heatClean, notSupported
*/
def setAirConditionerMode(mode){

}

def setLevel(level){
	def code = state['temperature-' + level]
	if(code){
		playIRCmd(code)
        sendEvent(name:'level', value: level )
    }
}

def remoteAir1(){
	log.debug "remoteAire1 >> " + state['air-custom-1']
	playIRCmd(state['air-custom-1'])
}

def remoteAir2(){
	playIRCmd(state['air-custom-2'])
}

def remoteAir3(){
	playIRCmd(state['air-custom-3'])
}

def remoteAir4(){
	playIRCmd(state['air-custom-4'])
}

def remoteAir5(){
	playIRCmd(state['air-custom-5'])
}

def remoteAir6(){
	playIRCmd(state['air-custom-6'])
}

def remoteAir7(){
	playIRCmd(state['air-custom-7'])
}

def remoteAir8(){
	playIRCmd(state['air-custom-8'])
}

def remoteAir9(){
	playIRCmd(state['air-custom-9'])
}

def remoteAir10(){
	playIRCmd(state['air-custom-10'])
}

def remoteAir11(){
	playIRCmd(state['air-custom-11'])
}

def remoteAir12(){
	playIRCmd(state['air-custom-12'])
}

def remoteAir13(){
	playIRCmd(state['air-custom-13'])
}

def remoteAir14(){
	playIRCmd(state['air-custom-14'])
}

def remoteAir15(){
	playIRCmd(state['air-custom-15'])
}

def playIRCmdByID(id){
	playIRCmd(state[id])
}

def playIRCmd(code){
	if(code == null || code == ""){
    	log.error("Non exist code")
    	return;
    }
    
    def body = [
        "id": state.id,
        "cmd": "playIRByCode",
        "data": code
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def updated() {
}

def sendCommand(options, _callback){
	def myhubAction = new hubitat.device.HubAction(options, null, [callback: _callback])
    sendHubCommand(myhubAction)
}

def makeCommand(body){
	def options = [
     	"method": "POST",
        "path": "/control",
        "headers": [
        	"HOST": state.app_url,
            "Content-Type": "application/json"
        ],
        "body":body
    ]
    return options
}
