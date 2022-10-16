// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
public class intake extends SubsystemBase {
  /** Creates a new intake. */
  private static CANSparkMax inLeft;
  private static CANSparkMax inRight;
  public intake() {
    inLeft = new CANSparkMax(12,MotorType.kBrushless);
    inRight = new CANSparkMax(13,MotorType.kBrushless);

  }
  public void setspeeds(double left, double right){
    inLeft.set(left);
    inRight.set(-right);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
