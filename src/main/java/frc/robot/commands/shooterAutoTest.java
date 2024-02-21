// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class shooterAutoTest extends ParallelCommandGroup {
  /** Creates a new shooterAutoTest. */
  public shooterAutoTest(shooter m_Shooter, Conveyor m_Conveyor) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new InstantCommand(() -> m_Conveyor.runConveyor()),
                new InstantCommand(() -> m_Shooter.setSpeed(Constants.Shooter.LRollerSpeed, Constants.Shooter.RRollerSpeed)));
  }
}
