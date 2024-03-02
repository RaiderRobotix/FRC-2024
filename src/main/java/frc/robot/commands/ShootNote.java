// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.shooter;

public class ShootNote extends Command {
  /** Creates a new ShootNote. */

  private Conveyor s_Conveyor;
  private shooter s_Shooter;

  private boolean isFinished;
  private boolean noteInGullet;

  private int count;
  public ShootNote(Conveyor s_Conveyor,shooter s_Shooter ) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.s_Conveyor = s_Conveyor;
    this.s_Shooter = s_Shooter;

    addRequirements(s_Conveyor);
    addRequirements(s_Shooter);
    this.isFinished = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    noteInGullet = s_Conveyor.getLineBreakerVal();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    s_Shooter.setSpeed(Constants.Shooter.LRollerSpeed, Constants.Shooter.RRollerSpeed);
    new WaitCommand(0.5);
    if(noteInGullet && (count == 1)){
      s_Conveyor.runConveyor();
    }
    else{
      isFinished = true;
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_Conveyor.stopConveyor();
    s_Shooter.setSpeed(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}