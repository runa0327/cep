package com.cisdi.cisdipreview.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BIMFaceScene {
    private String name;  // 动态字段
    private String type = "Globe";
    private long projectId = 10000848931873L;
    private SceneSetting sceneSetting = new SceneSetting();
    private List<Resource> resources = new ArrayList<>();
    private String callback;

    // 构造方法设置动态资源
    public BIMFaceScene(String sceneName, String modelName, Long modelId, String fileName, String callback) {
        this.name = sceneName;
        this.resources.add(Resource.createTilesetLayer(modelName, modelId, fileName));
        this.resources.add(Resource.createTileLayer());
        this.resources.add(Resource.createTerrainLayer());
        this.callback = callback;
    }
}

@Data
class SceneSetting {
    private final BaseLatLon baseLatLon = new BaseLatLon(23.0, 115.56302);
    private final HomeView homeView = new HomeView(
            new Orientation(-1.5707963267948966, 0.0, 0.0),
            new Position(10000000.0, 23.0, 115.56302)
    );
}

@Data
class BaseLatLon {
    private final double lat;
    private final double lon;

    public BaseLatLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}

@Data
class HomeView {
    private final Orientation orientation;
    private final Position position;
}

@Data
class Orientation {
    private final double pitch;
    private final double roll;
    private final double yaw;
}

@Data
class Position {
    private final double alt;
    private final double lat;
    private final double lon;
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
class Resource {
    private String id;
    private String name;
    private long modelId;
    private String modelType;
    private String fileName;
    private String parentId;
    private Integer priority;
    private String typeName;
    private String maxLevel;
    private String url;

    // 创建动态TilesetLayer
    public static Resource createTilesetLayer(String name, long modelId, String fileName) {
        Resource resource = new Resource();
        resource.setId("tileset-layer");
        resource.setName(name);
        resource.setModelId(modelId);
        resource.setModelType("singleModel");
        resource.setFileName(fileName);
        resource.setParentId("root");
        resource.setPriority(3);
        resource.setTypeName("TilesetLayer");
        return resource;
    }

    // 创建固定TileLayer
    public static Resource createTileLayer() {
        Resource resource = new Resource();
        resource.setId("tile-layer");
        resource.setName("地图");
        resource.setParentId("root");
        resource.setPriority(1);
        resource.setTypeName("TileLayer");
        resource.setMaxLevel("18");
        resource.setUrl("https://t0.tianditu.gov.cn/img_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={x}&TILECOL={y}&tk=b730b52fa82d500fb108a86df18a78e3");
        return resource;
    }

    // 创建固定TerrainLayer
    public static Resource createTerrainLayer() {
        Resource resource = new Resource();
        resource.setId("terrain-layer");
        resource.setName("地形");
        resource.setParentId("root");
        resource.setPriority(1);
        resource.setTypeName("TerrainLayer");
        resource.setUrl("https://static.bimface.com/earth/dem/v1/");
        return resource;
    }
}