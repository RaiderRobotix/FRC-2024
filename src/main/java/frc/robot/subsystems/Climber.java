// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  private TalonFX climbMotor;
  public Climber() {
    this.climbMotor = new TalonFX(5);
    this.climbMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed){
    climbMotor.set(speed);
  }

  public void stop(){
    climbMotor.set(0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
