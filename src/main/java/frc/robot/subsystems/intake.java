// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class intake extends SubsystemBase {
  /** Creates a new intake. */
  
  private CANSparkMax l_intake;
  private CANSparkMax r_intake;

  public intake() {
    this.l_intake = new CANSparkMax(Constants.Intake.LIntakeMotorID, MotorType.kBrushless);
    this.r_intake = new CANSparkMax(Constants.Intake.RIntakeMotorID, MotorType.kBrushless);

  }

  public void setIntakeSpeed(double lspeed, double rspeed){
    l_intake.set(-lspeed);
    r_intake.set(-rspeed);
  }

  public void stop(){
    l_intake.set(0);
    r_intake.set(0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
