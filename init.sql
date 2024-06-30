-- member
CREATE TABLE IF NOT EXISTS `bookfriends`.`member` (
                                                      `memberID` VARCHAR(20) NOT NULL,
                                                      `memberPassword` TEXT NOT NULL,
                                                      `memberEmail` VARCHAR(50) NOT NULL,
                                                      `memberEmailChecked` BIT,
                                                      PRIMARY KEY (`memberID`)
);

-- Likey
CREATE TABLE IF NOT EXISTS `bookfriends`.`likey` (
                                                     `memberID` VARCHAR(20) NOT NULL,
                                                     `itemType` ENUM('POST', 'REVIEW', 'REPLY') NOT NULL,
                                                     `itemID` INT NOT NULL,
                                                     PRIMARY KEY (`memberID`, `itemType`, `itemID`),
                                                     CONSTRAINT `fk_likey_memberID`
                                                         FOREIGN KEY (`memberID`)
                                                             REFERENCES `bookfriends`.`member` (`memberID`)
                                                             ON DELETE CASCADE
);

-- Board
CREATE TABLE IF NOT EXISTS `bookfriends`.`board` (
                                                     `postID` INT NOT NULL AUTO_INCREMENT,
                                                     `memberID` VARCHAR(20) NOT NULL,
                                                     `postCategory` VARCHAR(20) NOT NULL,
                                                     `postTitle` VARCHAR(50) NOT NULL,
                                                     `postContent` TEXT NOT NULL,
                                                     `viewCount` INT NULL DEFAULT NULL,
                                                     `likeCount` INT NULL DEFAULT NULL,
                                                     `postDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                     PRIMARY KEY (`postID`),
                                                     CONSTRAINT `fk_board_memberID`
                                                         FOREIGN KEY (`memberID`)
                                                             REFERENCES `bookfriends`.`member` (`memberID`)
                                                             ON DELETE CASCADE
);

-- Review
CREATE TABLE IF NOT EXISTS `bookfriends`.`review` (
                                                      `reviewID` INT NOT NULL AUTO_INCREMENT,
                                                      `memberID` VARCHAR(20) NOT NULL,
                                                      `bookName` VARCHAR(500) NOT NULL,
                                                      `authorName` VARCHAR(100) NOT NULL,
                                                      `publisher` VARCHAR(20) NULL DEFAULT NULL,
                                                      `category` VARCHAR(20) NOT NULL,
                                                      `reviewTitle` VARCHAR(500) NOT NULL,
                                                      `reviewContent` TEXT NOT NULL,
                                                      `reviewScore` INT NOT NULL,
                                                      `registDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                      `likeCount` INT DEFAULT 0,
                                                      `viewCount` INT DEFAULT 0,
                                                      PRIMARY KEY (`reviewID`),
                                                      CONSTRAINT `fk_review_memberID`
                                                          FOREIGN KEY (`memberID`)
                                                              REFERENCES `bookfriends`.`member` (`memberID`)
                                                              ON DELETE CASCADE
);

-- Recruit
CREATE TABLE IF NOT EXISTS `bookfriends`.`recruit` (
                                                       `recruitID` INT NOT NULL AUTO_INCREMENT,
                                                       `memberID` VARCHAR(20) NOT NULL,
                                                       `recruitStatus` VARCHAR(20) NOT NULL DEFAULT 'recruiting',
                                                       `recruitTitle` VARCHAR(500) NOT NULL,
                                                       `recruitContent` TEXT NOT NULL,
                                                       `registDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                       `viewCount` INT NULL DEFAULT 0,
                                                       PRIMARY KEY (`recruitID`),
                                                       CONSTRAINT `fk_recruit_memberID`
                                                           FOREIGN KEY (`memberID`)
                                                               REFERENCES `bookfriends`.`member` (`memberID`)
                                                               ON DELETE CASCADE
);

-- Chat
CREATE TABLE IF NOT EXISTS `bookfriends`.`chat` (
                                                    `chatID` INT NOT NULL AUTO_INCREMENT,
                                                    `senderID` VARCHAR(20) NOT NULL,
                                                    `receiverID` VARCHAR(20) NOT NULL,
                                                    `message` TEXT NOT NULL,
                                                    `chatTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                    `chatRead` BIT NOT NULL DEFAULT 0,
                                                    PRIMARY KEY (`chatID`),
                                                    CONSTRAINT `fk_chat_senderID`
                                                        FOREIGN KEY (`senderID`)
                                                            REFERENCES `bookfriends`.`member` (`memberID`)
                                                            ON DELETE CASCADE,
                                                    CONSTRAINT `fk_chat_receiverID`
                                                        FOREIGN KEY (`receiverID`)
                                                            REFERENCES `bookfriends`.`member` (`memberID`)
                                                            ON DELETE CASCADE
);

-- Reply
CREATE TABLE IF NOT EXISTS `bookfriends`.`reply` (
                                                     `replyID` INT NOT NULL AUTO_INCREMENT,
                                                     `memberID` VARCHAR(20) NOT NULL,
                                                     `postID` INT NULL DEFAULT NULL,
                                                     `recruitID` INT NULL DEFAULT NULL,
                                                     `replyContent` TEXT NOT NULL,
                                                     `likeCount` INT NULL DEFAULT 0,
                                                     `replyDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                     PRIMARY KEY (`replyID`),
                                                     CONSTRAINT `fk_reply_memberID`
                                                         FOREIGN KEY (`memberID`)
                                                             REFERENCES `bookfriends`.`member` (`memberID`)
                                                             ON DELETE CASCADE,
                                                     CONSTRAINT `fk_reply_postID`
                                                         FOREIGN KEY (`postID`)
                                                             REFERENCES `bookfriends`.`board` (`postID`)
                                                             ON DELETE CASCADE,
                                                     CONSTRAINT `fk_reply_recruitID`
                                                         FOREIGN KEY (`recruitID`)
                                                             REFERENCES `bookfriends`.`recruit` (`recruitID`)
                                                             ON DELETE CASCADE
);

-- File
CREATE TABLE IF NOT EXISTS `bookfriends`.`file` (
                                                    `fileID` INT NOT NULL AUTO_INCREMENT,
                                                    `fileName` VARCHAR(255) NOT NULL,
                                                    `fileOriginName` VARCHAR(255) NOT NULL,
                                                    `filePath` VARCHAR(255) NOT NULL,
                                                    `recruitID` INT NULL DEFAULT NULL,
                                                    `postID` INT NULL DEFAULT NULL,
                                                    PRIMARY KEY (`fileID`),
                                                    CONSTRAINT `fk_file_postID`
                                                        FOREIGN KEY (`postID`)
                                                            REFERENCES `bookfriends`.`board` (`postID`)
                                                            ON DELETE CASCADE,
                                                    CONSTRAINT `fk_file_recruitID`
                                                        FOREIGN KEY (`recruitID`)
                                                            REFERENCES `bookfriends`.`recruit` (`recruitID`)
                                                            ON DELETE CASCADE
);
