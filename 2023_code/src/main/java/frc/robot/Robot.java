//TRY NOT TO CHANGE ANY OF THE CODE IT AS ALREADY GOOD IF YOU NEED TO CHANGE JUST CHANGE VALUES SUCH ROBOT SPEED LIMITER!!!!!!!!!!!

/* 
HOW TO PUSH THIS CODE TO THE BOT 
-------------------------------
1. MAKE SURE THAT YOURE CONNECTED TO THE BOTS RADIO (wifi if ur in the room but at comps you have to connect via ethernet)
2.FIND build.gradle ON THE FOLDER DROPDOWN ON THE LEFT
3. RIGHT CLICK AND CLICK BUILD ROBOT CODE AND WAIT FOR IT TO FINISH
4. RIGHT CLICK THE SAME FILE AND CLICK DEPLOY ROBOT CODE AND WAIT
5. NOW CODE IS PUSHED SO GO TO DRIVER STATION AND BLOW UP BOT
P.S MAKE SURE THE XBOX CONTROLLER IS PORT 0 AND JOYSTICK IS PORT 1 YOU CAN CHECK IN THE DRIVERSTATION!!!!

*/


//if marben is reading this code lets pray the bot isnt alr dead  JK JK.. or am i?????

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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;

public class Robot extends TimedRobot {
  private XboxController m_controller;
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
   private Joystick joy;
   private ADXRS450_Gyro gyro;
   @Override
  public void robotInit() {
    joy = new Joystick(1);
    intaker = new intake();
    launch = new launcher();
    index = new indexer();
    climb = new climber();
    m_controller = new XboxController(0);
    //drivetrain
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

    m_drive.arcadeDrive(-m_controller.getLeftY()*.75,m_controller.getRightX()*.5);
    intaker.setspeeds(0.0,0.0);
    index.setspeeds(0.0, 0.0);
    launch.setspeed(0.0);
    climb.setspeed(0.0);
//this is autonomous shooting only use if you have two balls! if not just comment out everything under up to and including line 110
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
    }
    if(m_controller.getPOV()==0){
      time.stop();
      time.reset();
      launch.makefalse();
    }
  if(m_controller.getAButton()){
    launch.setspeed(.7);
    }
    //this is for TESTING PURPOSES
   // if(m_controller.getPOV()==90){
   //  gyro.reset();
   // }
    if(m_controller.getRightBumper()){
      index.setspeeds(-.4,-.4);
    }
    if(m_controller.getLeftBumper()){
      index.setspeeds(.4,.4);
    }
    // greater than .05 because of drag on the trigger
    if(m_controller.getRightTriggerAxis()>.05){
     intaker.setspeeds(.2,.2);
    }
    if(m_controller.getLeftTriggerAxis()>.05){
      intaker.setspeeds(-.2,-.2);
    }
    if(Math.abs(joy.getY())>0.05){
      climb.setspeed(joy.getY());
    }
  }
  //keep gyro.reset() in auton init but only if youre testing you can bring it in auton periodic funcition so you dont have to restart bot every time 
  //(keep in mind that it will take 10 years to load because gyro calibrates when you reset)
    @Override
    public void autonomousInit() {
    gyro.reset();
     time.reset();
     time.start();
    }

  @Override
//TWO BALL AUTON

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
else if(now>11.0){
  m_drive.arcadeDrive(-.6,0);

}
else if(Math.abs(gyro.getAngle())<167){
 m_drive.arcadeDrive(0, .6);
 launch.setspeed(.7);
 }
 else if(now>8.0){
  m_drive.arcadeDrive(.6,0.0);
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
}
else if(now>5.25){

}
else if(now>3.25){
  m_drive.arcadeDrive(-.6,0);
}

  }
}

