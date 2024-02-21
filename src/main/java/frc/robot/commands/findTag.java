// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.Arm;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.limelight;
import frc.robot.commands.*;

public class findTag extends Command {
  /** Creates a new findTag. */

  private Swerve s_Swerve;
  private limelight s_Limelight;
  private frc.robot.subsystems.Arm s_Arm;
  private Translation2d speeds;
  private double tv;
  private setArmPosition armPosition;


  public findTag(Swerve s_Swerve,frc.robot.subsystems.Arm s_Arm, limelight s_Limelight) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.s_Swerve = s_Swerve;
    addRequirements(s_Swerve);

    this.s_Limelight = s_Limelight;
    addRequirements(s_Limelight);

    this.s_Arm = s_Arm;
    addRequirements(s_Arm);

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
      if(s_Limelight.getTx() == 27 /*NEED TO CHANGE VALUE BASED ON TESTING */){
        armPosition = new setArmPosition(s_Arm, s_Limelight.getEstimatedPotValue(s_Limelight.estimateDistance()));
            //TODO NEED TO ADD GET ESTIMATED POT VALUE METHOD
        
      }
      else{
        if(s_Limelight.getTx() > 27){
          //Have to change the 27 and make it so robot moves till 27
        }
        else if(s_Limelight.getTx() < 27){}
          //Have ot change the 27 and make so robot moves till 27
      }
        
      //s_Limelight.estimateDistance();

    }
    else{
      s_Swerve.drive(speeds, 0.5, false, true);
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
