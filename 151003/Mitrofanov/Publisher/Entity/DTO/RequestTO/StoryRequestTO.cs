﻿namespace Publisher.Entity.DTO.RequestTO
{
    public record class StoryRequestTO(int Id, int EditorId, string Title, string Content, DateTime Created, DateTime Modified);
}
