/**
 *  Xiaomi Power Strip (v.0.0.1)
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
	definition (name: "Xiaomi Power Strip", namespace: "fison67", author: "fison67") {
    	capability "Actuator"
        capability "Switch"				
        capability "Power Meter"
        capability "Energy Meter"			//"on", "off"
        capability "Temperature Measurement"	
        capability "Configuration"
        capability "Sensor"
        capability "Outlet"
        capability "Refresh"
         
        attribute "mode", "string"
        attribute "current", "string"
        
        attribute "lastCheckin", "Date"
        
        command "wifiLedOn"
        command "wifiLedOff"
        command "realTimePowerOn"
        command "realTimePowerOff"
        
        command "setModeGreen"
        command "setModeNormal"
	}

	simulator { }

	preferences {
		input name: "historyDayCount", type:"number", title: "Day for History Graph", description: "", defaultValue:1, displayDuringSetup: true
		input name: "historyTotalDayCount", type:"number", title: "Total Day for History Graph", description: "0 is max", defaultValue:7, range: "2..7", displayDuringSetup: true
        
		input name: "temperatureHistoryDataMaxCount", type:"number", title: "Temperature Graph Data Max Count", description: "0 is max", defaultValue:0, displayDuringSetup: true
		input name: "powerMeterHistoryDataMaxCount", type:"number", title: "PowerMeter Graph Data Max Count", description: "0 is max", defaultValue:0, displayDuringSetup: true
		input name: "energyMeterHistoryDataMaxCount", type:"number", title: "EnergyMeter Graph Data Max Count", description: "0 is max", defaultValue:0, displayDuringSetup: true
        
	}
    
	tiles {
		multiAttributeTile(name:"switch", type: "generic", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'${name}', action:"off", icon:"https://github.com/fison67/mi_connector/blob/master/icons/powerStrip_on.png?raw=true", backgroundColor:"#00a0dc", nextState:"turningOff"
                attributeState "off", label:'${name}', action:"on", icon:"https://github.com/fison67/mi_connector/blob/master/icons/powerStrip_off.png?raw=true", backgroundColor:"#ffffff", nextState:"turningOn"
                
                attributeState "turningOn", label:'${name}', action:"off", icon:"https://github.com/fison67/mi_connector/blob/master/icons/powerStrip_on.png?raw=true", backgroundColor:"#00a0dc", nextState:"turningOff"
                attributeState "turningOff", label:'${name}', action:"on", icon:"https://github.com/fison67/mi_connector/blob/master/icons/powerStrip_off.png?raw=true", backgroundColor:"#ffffff", nextState:"turningOn"
			}
            
            tileAttribute("device.lastCheckin", key: "SECONDARY_CONTROL") {
    			attributeState("default", label:'Updated: ${currentValue}',icon: "st.Health & Wellness.health9")
            }
		}
        
        valueTile("power", "device.power", width:2, height:2, inactiveLabel: false, decoration: "flat" ) {
        	state "power", label: 'Meter\n${currentValue} w', defaultState: true
		}
        
        valueTile("current", "device.current", width:2, height:2, inactiveLabel: false, decoration: "flat") {
            state "current", label:'${currentValue}'
        }   
        
        valueTile("temperature", "device.temperature", width:2, height:2, inactiveLabel: false, decoration: "flat") {
            state "temperature", label:'${currentValue}°', unit:"°"
        }    
        
        standardTile("wifiLed", "device.wifiLed", inactiveLabel: false, width: 2, height: 2, canChangeIcon: true) {
            state "on", label:'Led ${name}', action:"wifiLedOff", backgroundColor:"#00a0dc", nextState:"turningOff"
            state "off", label:'Led ${name}', action:"wifiLedOn", backgroundColor:"#ffffff", nextState:"turningOn"
             
        	state "turningOn", label:'....', action:"wifiLedOff", backgroundColor:"#00a0dc", nextState:"turningOff"
            state "turningOff", label:'....', action:"wifiLedOn", backgroundColor:"#ffffff", nextState:"turningOn"
        }
        /* 
        standardTile("realTime", "device.realTime", inactiveLabel: false, width: 2, height: 2, canChangeIcon: true) {
            state "on", label:'${name}', action:"realTimePowerOn", backgroundColor:"#00a0dc", nextState:"turningOff"
            state "off", label:'${name}', action:"realTimePowerOff", backgroundColor:"#42f46b", nextState:"turningOn"
             
        	state "turningOn", label:'....', action:"realTimePowerOn", backgroundColor:"#00a0dc", nextState:"turningOff"
            state "turningOff", label:'....', action:"realTimePowerOff", backgroundColor:"#ffffff", nextState:"turningOn"
        }
        */
        standardTile("mode", "device.mode", inactiveLabel: false, width: 2, height: 2, canChangeIcon: true) {
            state "normal", label:'${name}', action:"setModeGreen", backgroundColor:"#00a0dc", nextState:"turningOff"
            state "green", label:'${name}', action:"setModeNormal", backgroundColor:"#42f46b", nextState:"turningOn"
             
        	state "turningOn", label:'....', action:"setModeGreen", backgroundColor:"#00a0dc", nextState:"turningOff"
            state "turningOff", label:'....', action:"setModeNormal", backgroundColor:"#ffffff", nextState:"turningOn"
        }
        
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label:"", action:"refresh", icon:"st.secondary.refresh"
        }
	
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

def setInfo(String app_url, String id) {
	log.debug "${app_url}, ${id}"
	state.app_url = app_url
    state.id = id
}

def setExternalAddress(address){
	log.debug "External Address >> ${address}"
	state.externalAddress = address
}

def setStatus(params){
    log.debug "${params.key} >> ${params.data}"
 
 	switch(params.key){
    case "mode":
    	sendEvent(name:"mode", value: params.data )
    	break;
    case "power":
        sendEvent(name:"switch", value: (params.data == "true" ? "on" : "off"))
    	break;
    case "powerConsumeRate":
    	sendEvent(name:"power", value: params.data )
    	break;
    case "temperature":
    	sendEvent(name:"temperature", value: params.data.replace(" C","").toFloat()/2)
    	break;
    case "current":
    	sendEvent(name:"current", value: params.data )
    	break;
    case "wifiLed":
    	sendEvent(name:"wifiLed", value: params.data )
    	break;
    }
    
    def now = new Date().format("yyyy-MM-dd HH:mm:ss", location.timeZone)
    sendEvent(name: "lastCheckin", value: now)
}

def realTimePowerOn(){
	log.debug "setRealTimePowerOn >> ${state.id}"
    def body = [
        "id": state.id,
        "cmd": "realTimePower",
        "data": "on"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def realTimePowerOff(){
	log.debug "setRealTimePowerOff >> ${state.id}"
    def body = [
        "id": state.id,
        "cmd": "realTimePower",
        "data": "off"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def wifiLedOn(){
	log.debug "setWifiLedOn >> ${state.id}"
    def body = [
        "id": state.id,
        "cmd": "wifiLed",
        "data": "on"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def wifiLedOff(){
	log.debug "setWifiLedOff >> ${state.id}"
    def body = [
        "id": state.id,
        "cmd": "wifiLed",
        "data": "off"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def setModeGreen(){
	log.debug "setModeGreen >> ${state.id}"
    def body = [
        "id": state.id,
        "cmd": "mode",
        "data": "green"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def setModeNormal(){
	log.debug "setModeNormal >> ${state.id}"
    def body = [
        "id": state.id,
        "cmd": "mode",
        "data": "normal"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def on(){
	log.debug "Off >> ${state.id}"
    def body = [
        "id": state.id,
        "cmd": "power",
        "data": "on"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def off(){
	log.debug "Off >> ${state.id}"
	def body = [
        "id": state.id,
        "cmd": "power",
        "data": "off"
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def callback(hubitat.device.HubResponse hubResponse){
	def msg
    try {
        msg = parseLanMessage(hubResponse.description)
		def jsonObj = new JsonSlurper().parseText(msg.body)
     
     	sendEvent(name:"temperature", value: (jsonObj.state.temperature).toFloat()/2)
     	sendEvent(name:"powerMeter", value: jsonObj.state.powerConsumeRate)
     	sendEvent(name:"switch", value: jsonObj.state.power ? "on" : "off")
     	sendEvent(name:"wifiLed", value: jsonObj.state.wifiLed)
     	sendEvent(name:"mode", value: jsonObj.state.mode)
        
    } catch (e) {
        log.error "Exception caught while parsing data: "+e;
    }
}

def refresh(){
	log.debug "Refresh"
    def options = [
     	"method": "GET",
        "path": "/devices/get/${state.id}",
        "headers": [
        	"HOST": state.app_url,
            "Content-Type": "application/json"
        ]
    ]
    sendCommand(options, callback)
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
