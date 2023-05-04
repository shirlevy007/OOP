public class RendererFactory {
    public Renderer buildRenderer (String type, int size){
        Renderer renderer = null;
        switch (type.toLowerCase()){
            case "none":
                renderer = new VoidRenderer();
                break;
            case "console":
                renderer = new ConsoleRenderer(size);
                break;
        }
        return renderer;
    }
}
