CREATE TABLE `match` (
                         `id` INT AUTO_INCREMENT PRIMARY KEY,
                         `player1_id` INT NOT NULL,
                         `player2_id` INT NOT NULL,
                         `player1_name` VARCHAR(50) NOT NULL,
                         `player2_name` VARCHAR(50) NOT NULL,
                         `large_score` VARCHAR(20) NOT NULL,
                         `small_scores` TEXT,
                         `winner_id` INT NOT NULL,
                         `winner_name` VARCHAR(50) NOT NULL,
                         `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);