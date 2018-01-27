package NXT;

import lejos.nxt.*;


public class NXTControl {
	
	private static int MOTOR_SPEED=40;
	private static UltrasonicSensor USS=new UltrasonicSensor(SensorPort.S4);
	
	private static void MoveCardToCenter()
	{
		
		Motor.B.setPower(MOTOR_SPEED);
		Motor.C.setPower(MOTOR_SPEED);
		
		while(USS.getDistance()>=6)
		{
			Motor.B.forward();
			Motor.C.forward();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Motor.B.stop();
		Motor.C.stop();
		
		//////////////////////////////////////////
		
		
		
		Motor.B.backward();
		while(USS.getDistance()<=12);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Motor.B.stop();
		
		/////////////////////////////////////////
		Motor.B.flt();
		Motor.C.flt();
		
	}
	
	public static void BringCardToTop()
	{
		MoveCardToCenter();
		
		Motor.C.setPower(50);
		Motor.C.forward();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Motor.C.stop();
		
		Motor.C.setPower(115);
		while(USS.getDistance()>6)
		{
			Motor.C.backward();
		}
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Motor.C.stop();
		
		Motor.C.stop();
		Motor.C.flt();
	}
	
	public static void PopCard()
	{
		MoveCardToCenter();
		Motor.C.setPower(70);
		
		Motor.C.forward();
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Motor.C.stop();
		Motor.C.flt();
	}

}
