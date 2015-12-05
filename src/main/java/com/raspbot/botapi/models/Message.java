package com.raspbot.botapi.models;

import com.raspbot.botapi.models.files.*;

import java.util.Date;

public class Message {
    public int MessageId;

    public User From;

    public Date Date;

    public Chat Chat;

    public User ForwardFrom;

    public Date ForwardDate;

    public Message ReplyToMessage;

    public String Text;

    public Audio Audio;

    public Document Document;

    public PhotoSize[] Photo;

    public Sticker Sticker;

    public Video Video;

    public Voice Voice;

    public String Caption;

    public Location Location;

    public File File;

    public User NewChatParticipant;

    public User LeftChatParticipant;

    public String NewChatTitle;

    public PhotoSize[] NewChatPhoto;

    public Boolean DeleteChatPhoto;

    public Boolean GroupChatCreated;

    public Boolean SupergroupChatCreated;

    public Boolean ChannelChatCreated;

    public int MigrateToChatId;

    public int MigrateFromChatId;
}
