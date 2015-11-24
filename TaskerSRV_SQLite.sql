--DROP DATABASE IF EXISTS TaskerSRV;

--CREATE DATABASE TaskerSRV;

--USE TaskerSRV;

-- tables
-- Table Admin
CREATE TABLE Admin (
    adminId INTEGER PRIMARY KEY,
    username varchar(20)  NOT NULL,
    password varchar(20)  NOT NULL,
    CONSTRAINT Admin_pk PRIMARY KEY (adminId)
);

-- Table Task
CREATE TABLE Task (
    taskId INTEGER PRIMARY KEY,
    title varchar(40)  NOT NULL,
    startDate date  NOT NULL,
    endDate date  NOT NULL,
    taskStatus int  NOT NULL,
    TeamMember_email varchar(80)  NOT NULL,
    UNIQUE INDEX taskId (taskId),
    CONSTRAINT Task_pk PRIMARY KEY (taskId)
);

-- Table TaskElement
CREATE TABLE TaskElement (
    taskElementId INTEGER PRIMARY KEY,
    description varchar(250)  NOT NULL,
    comments varchar(250)  NOT NULL,
    Task_taskId bigint  NOT NULL,
    UNIQUE INDEX taskElementId (taskElementId),
    CONSTRAINT TaskElement_pk PRIMARY KEY (taskElementId)
);

-- Table TeamMember
CREATE TABLE TeamMember (
    email varchar(80)  NOT NULL,
    firstName varchar(40)  NOT NULL,
    lastName varchar(40)  NOT NULL,
    password varchar(20)  NOT NULL,
    UNIQUE INDEX email (email),
    CONSTRAINT TeamMember_pk PRIMARY KEY (email)
);





-- foreign keys
-- Reference:  TaskElement_Task (table: TaskElement)


ALTER TABLE TaskElement ADD CONSTRAINT TaskElement_Task FOREIGN KEY TaskElement_Task (Task_taskId)
    REFERENCES Task (taskId)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
-- Reference:  Task_TeamMember (table: Task)


ALTER TABLE Task ADD CONSTRAINT Task_TeamMember FOREIGN KEY Task_TeamMember (TeamMember_email)
    REFERENCES TeamMember (email)
    ON DELETE CASCADE
    ON UPDATE CASCADE;



-- End of file.

