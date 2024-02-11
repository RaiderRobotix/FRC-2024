// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class arm extends SubsystemBase {
  /** Creates a new arm. */
  private TalonFX armMotor;
  public arm() {

    this.armMotor = new TalonFX(1);
    this.armMotor.setNeutralMode(NeutralModeValue.Brake);

  }

  public void open(double speed){
    armMotor.set(speed);
  }

  public void close(double speed){
    armMotor.set(speed);
  }

  public void stop(){
    armMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
