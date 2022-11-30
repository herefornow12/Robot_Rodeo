
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.SPI;

public class Robot extends TimedRobot {
  private XboxController m_controller;
  private XboxController m_controller2;
  private indexer index;
  private intake intaker;
  private launcher launch;
   private CANSparkMax m_leftLeader;
   private CANSparkMax m_leftFollower;
   private CANSparkMax m_rightLeader;
   private CANSparkMax m_rightFollower;
   private MotorControllerGroup dtRight;
   private MotorControllerGroup dtLeft;
   private DifferentialDrive m_drive;
   private Timer time;
   private climber climb;
   //private Joystick joy;
   private ADXRS450_Gyro gyro;
   @Override
  public void robotInit() {
    //joy = new Joystick(1);
    intaker = new intake();
    launch = new launcher();
    index = new indexer();
    climb = new climber();
    m_controller = new XboxController(0);
    m_controller2 = new XboxController(1);
    m_leftLeader = new CANSparkMax(3, MotorType.kBrushed);
    m_leftFollower = new CANSparkMax(4,MotorType.kBrushed);
    m_rightLeader = new CANSparkMax(9, MotorType.kBrushed);
    m_rightFollower = new CANSparkMax(2, MotorType.kBrushed);
    dtRight= new MotorControllerGroup(m_rightLeader,m_rightFollower);
    dtLeft= new MotorControllerGroup(m_leftLeader,m_leftFollower);
    dtLeft.setInverted(true);
    m_drive = new DifferentialDrive(dtLeft,dtRight);
    gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();
    gyro.reset();
    gyro.calibrate();
    time = new Timer();
  }
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Left Y", m_controller.getLeftY());
    SmartDashboard.putNumber("anlge", gyro.getAngle());
    SmartDashboard.putNumber("Right X", m_controller.getRightX());

    m_drive.arcadeDrive(-m_controller.getLeftY()*.70,m_controller.getRightY()*.5);
    intaker.setspeeds(0.0,0.0);
    index.setspeeds(0.0, 0.0);
    launch.setspeed(0.0);
    climb.setspeed(0.0);
    
    //Brian stuff he put here, for the 8th grade thing
    if(m_controller.getBButtonPressed()){
    doSpin();
    }
    if(m_controller.getAButton()){
    rev(); 
    }

    public void doSpin()
    {
    for (int q = 0; q<50; q++)
    {m_drive.arcadeDrive(-100,0);}
    for (int p = 0; p<50; p++)
    {m_drive.arcadeDrive(0,100);}
    }
    public void rev()
    {
    for (int p = 0; p<50; p++)
    {intaker.setspeeds(.2,.2);} 
    }
    
    
    
    
    
    /*
    if(m_controller.getBButtonPressed()){
      launch.makeTrue();
    }
    if(launch.getTrue()){
     if(time.get()==0.0){
       time.reset();
       time.start();
     }
     SmartDashboard.putNumber("timesseiseisis", time.get());
     double timer = 6.0-time.get();
     if(timer>3.5){
       launch.setspeed(.7);
     }
     else if(timer>3.25){
     launch.setspeed(.7);
     index.setspeeds(-.4,-.4);
     }
     else if(timer>.75){
     launch.setspeed(.7);
     index.setspeeds(0.0,0.0);
     }
     else if(timer>.25){
     launch.setspeed(.7);
     index.setspeeds(-.4,-.4);
     intaker.setspeeds(.2,.2);
     } 
     else{
       launch.makefalse();
       time.stop();
       time.reset();
     }
     
    }*/

    
    //2nd driver spins the launcher!!!!!
  if(m_controller2.getAButton()){
    launch.setspeed(.705);
    }
    /*if(m_controller.getPOV()==0){
      time.stop();
      time.reset();
      launch.makefalse();
    }
    */
    if(m_controller.getPOV()==90){
     gyro.reset();
    }
    //2nd driver indexes!!!
    if(m_controller2.getRightBumper()){
      index.setspeeds(-.4,-.4);
    }
    if(m_controller2.getLeftBumper()){
      index.setspeeds(.4,.4);
    }
    //Main driver intakes!
    if(m_controller.getRightTriggerAxis()>.05){
     intaker.setspeeds(.2,.2);
    }
    if(m_controller.getLeftTriggerAxis()>.05){
      intaker.setspeeds(-.2,-.2);
    }
    //2nd driver climbs!
    if(m_controller2.getXButton()){
      climb.setspeed(1);
    }
    if(m_controller2.getYButton()){
      climb.setspeed(-1);
    }
    
  }
    @Override
    public void autonomousInit() {
      gyro.reset();
     time.reset();
     time.start();
    }

  @Override
  public void autonomousPeriodic() {


SmartDashboard.putNumber("angle", gyro.getAngle());
SmartDashboard.putNumber("timer", time.get());
SmartDashboard.putNumber("angle", gyro.getAngle());
launch.setspeed(0.0);
climb.setspeed(0.0);
index.setspeeds(0.0,0.0);
intaker.setspeeds(0.0,0.0);
m_drive.arcadeDrive(0.0,0.0);
dtLeft.set(0.0);
dtRight.set(0.0);
Double now = 15.0-time.get();
if(now >13.0){
  m_drive.arcadeDrive(.6,0);
  intaker.setspeeds(.15, .15);
}
//else if(Math.abs(gyro.getAngle())<167){
  //m_drive.arcadeDrive(0, .6);
//}
else if(now>12.0){
  m_drive.arcadeDrive(-.6,0);

}
else if(Math.abs(gyro.getAngle())<167){
 m_drive.arcadeDrive(0, .6);
 }
 else if(now>8.0){
  m_drive.arcadeDrive(.75,0);
  launch.setspeed(.7);
}
else if(now>7.75){
  launch.setspeed(.7);
  index.setspeeds(-.4, -.4);
}
else if(now>6.75){
  launch.setspeed(.7);
}
else if(now>5.75){
  launch.setspeed(.7);
  index.setspeeds(-.4, -.4);
  intaker.setspeeds(.15, .15);
}
else if(now>5.25){

}
else if(now>3.25){
  m_drive.arcadeDrive(-.6,0);
}

  }
}

