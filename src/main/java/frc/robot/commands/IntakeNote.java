// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.intake;

public class IntakeNote extends Command {
  /** Creates a new IntakeNote. */
  private intake s_Intake;
  private Conveyor s_Conveyor;

  private int count;
  private Timer time;

  private boolean noteInGullet;
  private boolean isFinished;

  public IntakeNote(intake s_Intake, Conveyor s_Conveyor ) {
    this.s_Intake = s_Intake;
    this.s_Conveyor = s_Conveyor;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_Intake);
    addRequirements(s_Conveyor);
    this.isFinished = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    noteInGullet = s_Conveyor.getLineBreakerVal();
    time.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (noteInGullet) {
      time.start();
      count++;
    }
    if((count == 1)){
      if(time.get() < 0.2){
        s_Conveyor.runConveyor();
      }
      else{
        s_Conveyor.stopConveyor();
        isFinished = true;
      }
    } 
    // else if(noteInGullet && (count == 1)){
    //   s_Conveyor.runConveyor();
    // }
    else{
      s_Conveyor.runConveyor();
      s_Intake.setIntakeSpeed(Constants.Intake.LIntakeMotorSpeed, Constants.Intake.RIntakeMotorSpeed);
      
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_Conveyor.stopConveyor();
    s_Intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}