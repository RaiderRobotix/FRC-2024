// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.limelight;

public class findTag extends Command {
  /** Creates a new findTag. */

  private Swerve s_Swerve;
  private limelight s_Limelight;
  private Translation2d speeds;
  private double tv;


  public findTag(Swerve s_Swerve, limelight s_Limelight) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.s_Swerve = s_Swerve;
    addRequirements(s_Swerve);

    this.s_Limelight = s_Limelight;
    addRequirements(s_Limelight);

    this.speeds = new Translation2d(0 * Constants.Swerve.maxSpeed, 0 * Constants.Swerve.maxSpeed);


  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // s_Limelight.Update_Tracking();

    // //System.out.println(s_Limelight.getTV());
    if(s_Limelight.findTarget()){
      s_Swerve.drive(speeds, 0, false, true);
      s_Limelight.estimateDistance();
    }
    else{
      s_Swerve.drive(speeds, 0, false, true);
    }

    //s_Limelight.findTarget();
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
