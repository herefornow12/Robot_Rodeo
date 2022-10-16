// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
public class indexer extends SubsystemBase {
  /** Creates a new intake. */
  private static CANSparkMax inTop;
  private static CANSparkMax inBot;
  public indexer() {
    inTop = new CANSparkMax(8,MotorType.kBrushed);
    inBot = new CANSparkMax(11,MotorType.kBrushed);

  }
  public void setspeeds(double top, double bot){
    inTop.set(top);
    inBot.set(bot);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
