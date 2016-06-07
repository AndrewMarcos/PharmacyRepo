
package romance.pharmacy.andrew_marcos.pharmacyrepo.jsondata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Entry {

    @SerializedName("id")
    @Expose
    private Id_ id;
    @SerializedName("updated")
    @Expose
    private Updated_ updated;
    @SerializedName("category")
    @Expose
    private List<Category> category = new ArrayList<Category>();
    @SerializedName("title")
    @Expose
    private Title_ title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("link")
    @Expose
    private List<Link> link = new ArrayList<Link>();

    /**
     * 
     * @return
     *     The id
     */
    public Id_ getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Id_ id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The updated
     */
    public Updated_ getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The updated
     */
    public void setUpdated(Updated_ updated) {
        this.updated = updated;
    }

    /**
     * 
     * @return
     *     The category
     */
    public List<Category> getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(List<Category> category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The title
     */
    public Title_ getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(Title_ title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The content
     */
    public Content getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The link
     */
    public List<Link> getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(List<Link> link) {
        this.link = link;
    }

}
