// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveAtSpeed;
import frc.robot.commands.pickup;
import frc.robot.commands.runShooterConveyor;
import frc.robot.commands.setArmPosition;
import frc.robot.commands.stopPickup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class middleAuton extends SequentialCommandGroup {
  /** Creates a new middleAuton. */
  public middleAuton(Swerve m_Swerve, Arm m_Arm, Conveyor m_Conveyor, shooter m_Shooter, intake m_Intake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), enew BarCommand()); //NEED TO ADD LINEBREAKER
    addCommands(
      new setArmPosition(m_Arm, 0.108),
      new runShooterConveyor(m_Conveyor, m_Shooter),
      new WaitCommand(2),
      new pickup(m_Intake, m_Arm, m_Conveyor),
      new ParallelCommandGroup(
      new setArmPosition(m_Arm, 0.05),
      new DriveAtSpeed(m_Swerve, -0.2, 0,0,1.5)),
      //new pickup(m_Intake, m_Arm, m_Conveyor),
     // new DriveAtSpeed(m_Swerve, -0.4, 0,0,0.5),
      //new pickup(m_Intake, m_Arm, m_Conveyor),
      new WaitCommand(2.0),
      //new DriveAtSpeed(m_Swerve, 0.2, 0, 0, 1.5),
      new setArmPosition(m_Arm, 0.0717),
      new runShooterConveyor(m_Conveyor, m_Shooter));
      //new stopPickup(m_Intake, m_Arm, m_Conveyor));
    }
  }