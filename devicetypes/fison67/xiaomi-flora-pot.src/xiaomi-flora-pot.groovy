/**
 *  Xiaomi Mi Flora Pot (v.0.0.1)
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
import groovy.transform.Field

@Field 
LANGUAGE_MAP = [
    "temp": [
        "Korean": "온도",
        "English": "Temperature"
    ],
    "illuminance": [
        "Korean": "밝기",
        "English": "Illuminance"
    ],
    "moisture": [
        "Korean": "수분",
        "English": "Moisture"
    ],
    "fertility": [
        "Korean": "비옥",
        "English": "Fertility"
    ]
]

metadata {
	definition (name: "Xiaomi Flora Pot", namespace: "fison67", author: "fison67") {
        capability "Sensor"
        capability "Battery"
        capability "Temperature Measurement"
        capability "Relative Humidity Measurement"
        capability "Illuminance Measurement"
        
		capability "Refresh"
               
        attribute "lastCheckin", "Date"
        
        command "chartTemperature"
        command "chartMoisture"
        command "chartLux"
        command "chartFertility"
        command "chartTotalTemperature"
        command "chartTotalMoisture"
        command "chartTotalLux"
        command "chartTotalFertility"
	}

	simulator {
	}
    
        preferences {
    	input name: "selectedLang", title:"Select a language" , type: "enum", required: true, options: ["English", "Korean"], defaultValue: "English", description:"Language for DTH"
        
        input name: "totalChartType", title:"Total-Chart Type" , type: "enum", required: true, options: ["line", "bar"], defaultValue: "line", description:"Total Chart Type [ line, bar ]" 
        input name: "historyDayCount", type:"number", title: "Maximum days for single graph", required: true, description: "", defaultValue:1, displayDuringSetup: true
        input name: "historyTotalDayCount", type:"number", title: "Maximum days for total graph", required: true, description: "", defaultValue:7, range: "2..31", displayDuringSetup: true

	}

	tiles {
		multiAttributeTile(name:"humidity", type: "generic", width: 6, height: 4){
        
            tileAttribute("device.humidity", key: "PRIMARY_CONTROL") {
            	attributeState "val", label:'${currentValue}', icon:"https://github.com/fison67/mi_connector/blob/master/icons/mi-flora-pot.png?raw=true",
                backgroundColors:[
                     [value: 0, color: "#153591"],
                     [value: 5, color: "#1e9cbb"],
                     [value: 10, color: "#90d2a7"],
                     [value: 15, color: "#44b621"],
                     [value: 20, color: "#f1d801"],
                     [value: 25, color: "#d04e00"],
                     [value: 30, color: "#bc2323"],
                     [value: 44, color: "#1e9cbb"],
                     [value: 59, color: "#90d2a7"],
                     [value: 74, color: "#44b621"],
                     [value: 84, color: "#f1d801"],
                     [value: 95, color: "#d04e00"],
                     [value: 96, color: "#bc2323"]
                 ]
            }
            
            tileAttribute("device.battery", key: "SECONDARY_CONTROL") {
    			attributeState("default", label:'Battery: ${currentValue}%\n')
            }
            tileAttribute("device.lastCheckin", key: "SECONDARY_CONTROL") {
    			attributeState("default", label:'\nLast Update: ${currentValue}')
            }
		}
            
        valueTile("label_temperature", "device.label_temperature", decoration: "flat", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        
        valueTile("label_illuminance", "device.label_illuminance", decoration: "flat", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        
        valueTile("label_fertility", "device.label_fertility", decoration: "flat", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        
        valueTile("temperature", "device.temperature", width: 2, height: 2) {
            state "val", label:'${currentValue}°', defaultState: true,
                backgroundColors:[
                     [value: 0, color: "#153591"],
                     [value: 5, color: "#1e9cbb"],
                     [value: 10, color: "#90d2a7"],
                     [value: 15, color: "#44b621"],
                     [value: 20, color: "#f1d801"],
                     [value: 25, color: "#d04e00"],
                     [value: 30, color: "#bc2323"],
                     [value: 44, color: "#1e9cbb"],
                     [value: 59, color: "#90d2a7"],
                     [value: 74, color: "#44b621"],
                     [value: 84, color: "#f1d801"],
                     [value: 95, color: "#d04e00"],
                     [value: 96, color: "#bc2323"]
                ]
        }
        
        valueTile("illuminance", "device.illuminance", width: 2, height: 2) {
            state "val", label:'${currentValue}lx', defaultState: true,
                backgroundColors:[
                    [value: 100, color: "#153591"],
                    [value: 200, color: "#1e9cbb"],
                    [value: 300, color: "#90d2a7"],
                    [value: 600, color: "#44b621"],
                    [value: 900, color: "#f1d801"],
                    [value: 1200, color: "#d04e00"],
                    [value: 1500, color: "#bc2323"]
                ]
        }    
        
        valueTile("versions", "device.versions", decoration: "flat", width: 3, height: 1) {
            state "default", label:'${currentValue}'
        }
        
        valueTile("fertility", "device.fertility", width: 2, height: 2) {
            state "val", label:'${currentValue}', defaultState: true,
                backgroundColors:[
                    [value: 100, color: "#153591"],
                    [value: 200, color: "#1e9cbb"],
                    [value: 300, color: "#90d2a7"],
                    [value: 600, color: "#44b621"],
                    [value: 900, color: "#f1d801"],
                    [value: 1200, color: "#d04e00"],
                    [value: 1500, color: "#bc2323"]
                ]
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

def setLanguage(language){
    log.debug "Languge >> ${language}"
	state.language = language
    
    sendEvent(name:"label_temperature", value: LANGUAGE_MAP["temp"][language] )
    sendEvent(name:"label_illuminance", value: LANGUAGE_MAP["illuminance"][language] )
	sendEvent(name:"label_fertility", value: LANGUAGE_MAP["fertility"][language] )
}

def setExternalAddress(address){
	log.debug "External Address >> ${address}"
	state.externalAddress = address
}

def setStatus(params){
	log.debug "${params.key} : ${params.data}"
    
    def data = new JsonSlurper().parseText(params.data)
    log.debug data.sensor
    
    sendEvent(name:"battery", value: data.firmware.battery)
    sendEvent(name:"versions", value: 'version: ' + data.firmware.firmware)
    
    sendEvent(name:"temperature", value: data.sensor.temperature)
    sendEvent(name:"illuminance", value: data.sensor.lux)
    sendEvent(name:"humidity", value: data.sensor.moisture)
    sendEvent(name:"fertility", value: data.sensor.fertility)
    
    updateLastTime()
}

def updated() {
    setLanguage(settings.selectedLang)
}

def updateLastTime(){
	def now = new Date().format("yyyy-MM-dd HH:mm:ss", location.timeZone)
    sendEvent(name: "lastCheckin", value: now)
}

def getMac(){
	def mac = state.id
    return mac.substring(0,2) + ":" + mac.substring(2,4) + ":" + mac.substring(4,6) + ":" + mac.substring(6,8) + ":" + mac.substring(8,10) + ":" + mac.substring(10,12)
}
