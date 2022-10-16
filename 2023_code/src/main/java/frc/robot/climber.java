// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
public class climber extends SubsystemBase {
  /** Creates a new intake. */
  private static CANSparkMax climb;
  public climber() {
  climb = new CANSparkMax(10,MotorType.kBrushless);
  }
  public void setspeed(double spd){
    climb.set(spd);
  }

}
