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


public class arm extends SubsystemBase {
  /** Creates a new arm. */
  private TalonFX armMotor;
  private AnalogPotentiometer pot;

   private ShuffleboardTab tab = Shuffleboard.getTab("default");
    private GenericEntry potEntry = tab.add("Arm Pot", 0).withSize(2, 1).getEntry();
    private GenericEntry safetyEntry = tab.add("Arm Safety Hit", false).withSize(2, 1).getEntry();

  public arm() {

    this.armMotor = new TalonFX(1);
    this.armMotor.setNeutralMode(NeutralModeValue.Brake);

    this.pot = new AnalogPotentiometer(1);

  }

  public void setArmSpeed(double speed){
    armMotor.set(speed);
  }

  // public void close(double speed){
  //   armMotor.set(speed);
  // }

  public void stop(){
    armMotor.set(0);
  }

  public double getPotValue()
    {
        return this.pot.get();
    }

  public void runArmTHing(){
    
  }

  public void open(){
    if(lowerLimitHit()){
      setArmSpeed(0);
    }
    else{
      setArmSpeed(0.4);
    }
  }

  public void close(){
    if(lowerLimitHit()){
      setArmSpeed(0);
    }
    else{
      setArmSpeed(-0.4);
    }
  }

  public boolean upperLimitHit()
    {
        return getPotValue() >= 0.08;
    }

    public boolean lowerLimitHit()
    {
        return getPotValue() <= 0.07;
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //SmartDashboard.putNumber("Arm Pot", getPotValue());
    potEntry.setDouble(getPotValue());
    safetyEntry.setBoolean(upperLimitHit() || lowerLimitHit());
  }
}
