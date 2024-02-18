// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Arm extends SubsystemBase {
  /** Creates a new arm. */

  private TalonFX armMotor;
  private AnalogPotentiometer armPot;

  private ShuffleboardTab tab = Shuffleboard.getTab("default");
  private GenericEntry potEntry = tab.add("Arm Pot", 0).withSize(2, 1).getEntry();
  private GenericEntry safetyEntry = tab.add("Arm Safety Hit", false).withSize(2, 1).getEntry();

   

  public Arm() {

    this.armMotor = new TalonFX(Constants.Arm.TalonDeviceID);
    this.armMotor.setNeutralMode(NeutralModeValue.Brake);

    this.armPot = new AnalogPotentiometer(Constants.Arm.PotentiometerChannel);

  }

  public void setArmSpeed(double speed){
    armMotor.set(speed);
  }

  public void moveArmUp(double speed){
    armMotor.set(-speed);
  }

  public void moveArmDown(double speed){
    armMotor.set(speed);
  }

  public void stop(){
    armMotor.set(0);
  }

  public double getPotValue()
    {
        return this.armPot.get();
    }

  public boolean upperLimitHit()
    {
        return getPotValue() >= Constants.Arm.UpperSafety;
    }

  public boolean lowerLimitHit()
  {
      return getPotValue() <= Constants.Arm.LowerSafety;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //SmartDashboard.putNumber("Arm Pot", getPotValue());
    potEntry.setDouble(getPotValue());
    safetyEntry.setBoolean(upperLimitHit() || lowerLimitHit());
  }
}
