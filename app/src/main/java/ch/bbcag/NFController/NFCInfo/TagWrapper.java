package ch.bbcag.NFController.NFCInfo;

class TagWrapper {
    private final String id;
    TagTechList techList = new TagTechList();

    public TagWrapper(final String tagId) {
        id = tagId;
    }

    public final String getId() {
        return id;
    }
}