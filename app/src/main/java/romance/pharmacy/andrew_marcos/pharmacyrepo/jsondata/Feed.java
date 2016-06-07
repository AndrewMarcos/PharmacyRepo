
package romance.pharmacy.andrew_marcos.pharmacyrepo.jsondata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    @SerializedName("xmlns")
    @Expose
    private String xmlns;
    @SerializedName("xmlns$openSearch")
    @Expose
    private String xmlns$openSearch;
    @SerializedName("xmlns$batch")
    @Expose
    private String xmlns$batch;
    @SerializedName("xmlns$gs")
    @Expose
    private String xmlns$gs;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("updated")
    @Expose
    private Updated updated;
    @SerializedName("category")
    @Expose
    private List<Object> category = new ArrayList<Object>();
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("link")
    @Expose
    private List<Object> link = new ArrayList<Object>();
    @SerializedName("author")
    @Expose
    private List<Object> author = new ArrayList<Object>();
    @SerializedName("openSearch$totalResults")
    @Expose
    private OpenSearch$totalResults openSearch$totalResults;
    @SerializedName("openSearch$startIndex")
    @Expose
    private OpenSearch$startIndex openSearch$startIndex;
    @SerializedName("entry")
    @Expose
    private List<Entry> entry = new ArrayList<Entry>();

    /**
     * 
     * @return
     *     The xmlns
     */
    public String getXmlns() {
        return xmlns;
    }

    /**
     * 
     * @param xmlns
     *     The xmlns
     */
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    /**
     * 
     * @return
     *     The xmlns$openSearch
     */
    public String getXmlns$openSearch() {
        return xmlns$openSearch;
    }

    /**
     * 
     * @param xmlns$openSearch
     *     The xmlns$openSearch
     */
    public void setXmlns$openSearch(String xmlns$openSearch) {
        this.xmlns$openSearch = xmlns$openSearch;
    }

    /**
     * 
     * @return
     *     The xmlns$batch
     */
    public String getXmlns$batch() {
        return xmlns$batch;
    }

    /**
     * 
     * @param xmlns$batch
     *     The xmlns$batch
     */
    public void setXmlns$batch(String xmlns$batch) {
        this.xmlns$batch = xmlns$batch;
    }

    /**
     * 
     * @return
     *     The xmlns$gs
     */
    public String getXmlns$gs() {
        return xmlns$gs;
    }

    /**
     * 
     * @param xmlns$gs
     *     The xmlns$gs
     */
    public void setXmlns$gs(String xmlns$gs) {
        this.xmlns$gs = xmlns$gs;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Id getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Id id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The updated
     */
    public Updated getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The updated
     */
    public void setUpdated(Updated updated) {
        this.updated = updated;
    }

    /**
     * 
     * @return
     *     The category
     */
    public List<Object> getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(List<Object> category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The link
     */
    public List<Object> getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(List<Object> link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The author
     */
    public List<Object> getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(List<Object> author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The openSearch$totalResults
     */
    public OpenSearch$totalResults getOpenSearch$totalResults() {
        return openSearch$totalResults;
    }

    /**
     * 
     * @param openSearch$totalResults
     *     The openSearch$totalResults
     */
    public void setOpenSearch$totalResults(OpenSearch$totalResults openSearch$totalResults) {
        this.openSearch$totalResults = openSearch$totalResults;
    }

    /**
     * 
     * @return
     *     The openSearch$startIndex
     */
    public OpenSearch$startIndex getOpenSearch$startIndex() {
        return openSearch$startIndex;
    }

    /**
     * 
     * @param openSearch$startIndex
     *     The openSearch$startIndex
     */
    public void setOpenSearch$startIndex(OpenSearch$startIndex openSearch$startIndex) {
        this.openSearch$startIndex = openSearch$startIndex;
    }

    /**
     * 
     * @return
     *     The entry
     */
    public List<Entry> getEntry() {
        return entry;
    }

    /**
     * 
     * @param entry
     *     The entry
     */
    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

}
