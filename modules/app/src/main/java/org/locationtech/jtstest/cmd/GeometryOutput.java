package org.locationtech.jtstest.cmd;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKBWriter;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.locationtech.jts.io.gml2.GMLWriter;
import org.locationtech.jtstest.testbuilder.geom.GeometryUtil;
import org.locationtech.jtstest.testbuilder.io.SVGTestWriter;

/**
 * Outputs geometry in a specified format.
 * 
 * @author Admin
 *
 */
public class GeometryOutput {
  private CommandOutput out;

  public GeometryOutput(CommandOutput out) {
    this.out = out;
  }
  
  public void printGeometry(Geometry geom, String outputFormat) {
    String txt = null;
    if (outputFormat.equalsIgnoreCase(CommandOptions.FORMAT_WKT)
        || outputFormat.equalsIgnoreCase(CommandOptions.FORMAT_TXT)) {
      txt = geom.toString();
    }
    else if (outputFormat.equalsIgnoreCase(CommandOptions.FORMAT_WKB)) {
      txt = WKBWriter.toHex((new WKBWriter().write(geom)));
    }
    else if (outputFormat.equalsIgnoreCase(CommandOptions.FORMAT_GML)) {
      txt = (new GMLWriter()).write(geom);
    }
    else if (outputFormat.equalsIgnoreCase(CommandOptions.FORMAT_GEOJSON)) {
      txt = writeGeoJSON(geom);
    }
    else if (outputFormat.equalsIgnoreCase(CommandOptions.FORMAT_SVG)) {
      txt = SVGTestWriter.getSVG(geom, null);
    }
    
    if (txt == null) return;
    out.println(txt);
  }

  private static String writeGeoJSON(Geometry geom) {
    GeoJsonWriter writer = new GeoJsonWriter();
    writer.setEncodeCRS(false);
    return writer.write(geom);
  }
  
  public static String writeGeometrySummary(String label,
      Geometry g)
  {
    if (g == null) return "";
    StringBuilder buf = new StringBuilder();
    buf.append(label + " : ");
    buf.append(GeometryUtil.structureSummary(g));
    buf.append("    " + GeometryUtil.metricsSummary(g));
    return buf.toString();
  }

}
