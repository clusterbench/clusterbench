package org.jboss.clusterbench;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class CdiBean implements Serializable {

   private int serialId;

   public int getSerialId() {
      return serialId;
   }

   public void setSerialId(int serialId) {
      this.serialId = serialId;
   }
}
