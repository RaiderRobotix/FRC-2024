// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class gullet extends SubsystemBase {
  /** Creates a new gullet. */

  private TalonSRX conveyor;
  public gullet() {
    this.conveyor = new TalonSRX(7);
    

  }

  public void runConveyor(){
    conveyor.set(TalonSRXControlMode.PercentOutput, 0.2);
  }
  public void stopConveyor(){
    conveyor.set(TalonSRXControlMode.PercentOutput, 0);
  }

  public void reverseConveyor(){
    conveyor.set(TalonSRXControlMode.PercentOutput, -0.2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
