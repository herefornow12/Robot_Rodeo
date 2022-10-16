// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
public class launcher extends SubsystemBase {
  /** Creates a new intake. */
  private static CANSparkMax launch;
  private boolean isTrue;
  public launcher() {
   launch = new CANSparkMax(7,MotorType.kBrushless);
    isTrue=false;

  }
  public void setspeed(double spd){
    launch.set(-spd);
   
  }
  public void makefalse(){
    isTrue  = false;
  }
  public boolean getTrue(){
    return isTrue;
  }
  public void makeTrue(){
    isTrue=true;
  }
}
