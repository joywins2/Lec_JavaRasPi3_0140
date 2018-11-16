/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/*
...NetBeans :: wiringPiSetup: Must be root (Did you forget sudo ?)	
   https://raspberrypi.stackexchange.com/questions/54208/wiringpisetup-must-be-root-did-you-forget-sudo
*/

/*
...NetBeans :: WiringPi에서 "Unable to determine hardware version. I see : Hardware : BCM2835 '오류	
   https://qiita.com/jollyjoester/items/ba59e5d43e28b701f120
    증상 :
    $ sudo python3 led.py
    Unable to determine hardware version. I see : Hardware : BCM2835
    ,
     - expecting BCM2708 or BCM2709.
    If this is a genuine Raspberry Pi then please report this
    to projects@drogon.net. If this is not a Raspberry Pi then you
    are on your own as wiringPi is designed to support the
    Raspberry Pi ONLY.

    해결 방법 :
    제목의 오류를 그 그는 후에 "Kernel를 upgrade하기 전에 움직이고 있었어!"라고 기입이 어딘가에 있었으므로 4.9 계의 이전 4.4 계까지 다운 그레이드 보니 움직였다.

    당초 Kernel 버전은 4.9.24

    $ uname -a
    Linux rasp-jolly 4.9.24-v7 + # 993 SMP Wed Apr 26 18:01:23 BST 2017 armv7l GNU / Linux
    4.4 시스템의 최신 같아 녀석 의 해시를 사용하여 다운 그레이드

    $ sudo rpi-update 52241088c1da59a359110d39c1875cda56496764
    다시 시작

    $ sudo reboot
    재부팅 후 Kernel 버전

    $ uname -a
    Linux rasp-jolly 4.4.50-v7 + # 970 SMP Mon Feb 20 19:18:29 GMT 2017 armv7l GNU / Linux
    움직였다! (L 치카에 1 시간 이상 걸렸다 orz)
 */
public class Ch01012_JavaRunOnRaspberryPi3_LedOnOff  {  

    public static void main(String[] args) throws InterruptedException {

        System.out.println("<--Pi4J--> GPIO Control Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);

        // set shutdown state for this pin
        //...ref : https://www.programcreek.com/java-api-examples/?class=com.pi4j.io.gpio.GpioPinDigitalOutput&method=setShutdownOptions
        //   configure the pin shutdown behavior; these settings will be
        //   automatically applied to the pin when the application is terminated.
        pin.setShutdownOptions(true, PinState.LOW);

        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);

        // turn off gpio pin #01
        pin.low();
        System.out.println("--> GPIO state should be: OFF");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01 (should turn on)
        pin.toggle();
        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01  (should turn off)
        pin.toggle();
        System.out.println("--> GPIO state should be: OFF");

        Thread.sleep(5000);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("--> GPIO state should be: ON for only 1 second");
        pin.pulse(1000, true); // set second argument to 'true' use a blocking call

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();

        System.out.println("Exiting ControlGpioExample");
    }
}
