// Artifact Order Decoder:
public class ArtifactOrderDecoder {

    // Map AprilTag IDs to artifact color sequences
    private final Map<Integer, List<String>> artifactOrders = new HashMap<>();

    public ArtifactOrderDecoder() {
        artifactOrders.put(21, Arrays.asList("Green", "Purple", "Purple")); // GPP
        artifactOrders.put(22, Arrays.asList("Purple", "Green", "Purple")); // PGP
        artifactOrders.put(23, Arrays.asList("Purple", "Purple", "Green")); // PPG
    }

    public List<String> getArtifactOrder(int tagId) {
        return artifactOrders.getOrDefault(tagId, Collections.singletonList("Unknown Order"));
    }

    public boolean isValidMotifTag(int tagId) {
        return artifactOrders.containsKey(tagId);
    }
}
// Opmode example:
ArtifactOrderDecoder decoder = new ArtifactOrderDecoder();
int visibleTagId = getVisibleAprilTagId(); // From Limelight or VisionPortal

if (decoder.isValidMotifTag(visibleTagId)) {
    List<String> order = decoder.getArtifactOrder(visibleTagId);
    for (int i = 0; i < order.size(); i++) {
        telemetry.addData("Artifact Slot " + (i + 1), order.get(i));
    }
} else {
    telemetry.addData("Motif", "Unrecognized AprilTag ID: " + visibleTagId);
}
telemetry.update();
