package co.polarpublishing.common.constant;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Collection of book types.
 *
 * @author FMRGJ
 */
@Slf4j
public enum BookType {

  PAPERBACK(
      "paperback,perfect paperback,mass market paperback,pocket book,taschenbuch,broschiert,copertina flessibile,forniture assortite,rilegatura flessibile,turtleback,poche,broché,broch,tapa blanda,libro de bolsillo,edición de bolsillo",
      BookTypeClass.PRINTBOOK),
  HARDCOVER(
      "hardcover,gebundenes buch,gebundene ausgabe,copertina rigida,libro cartonato,cartonato,reli,relié,tapa dura,libro de bolsillo",
      BookTypeClass.PRINTBOOK),
  KINDLE(
      "kindle edition,kindle,kindle & comixology,digital-text,kindle edition with audio/video,kindle edition with audio,etextbook,kindle books,kindle ausgabe,digital,formato kindle,ebook kindle,format kindle,versión kindle,edición kindle con audio/vídeo,édition kindle",
      BookTypeClass.EBOOK),
  AUDIO(
      "audible audiobook,audible audiobooks,audiobook,audiobooks,hörbuch,audible hörbuch,audiolibro,audiolibro audible,unabridged audiobook,length,abridged audiobook,livres audio audible,audible audiolibro,livre audio",
      BookTypeClass.AUDIOBOOK),
  AUDIO_CD("audio cd,audio-cd,cd", BookTypeClass.BOOK_SUPPLEMENT),
  SUPPLEMENT("book supplement,karten,geschenkartikel", BookTypeClass.BOOK_SUPPLEMENT),
  SPIRAL_BOUND("spiral-bound,spiralbindung", BookTypeClass.PRINTBOOK),
  CALENDAR("calendar,kalender,calendario,calendrier", BookTypeClass.PRINTBOOK),
  SHEET_MUSIC("sheet music,musiknoten", BookTypeClass.PRINTBOOK),
  MAGAZINE("single issue magazine,magazine,magazin", BookTypeClass.PRINTBOOK),
  UNKNOWN_BINDING("unknown binding", BookTypeClass.PRINTBOOK),
  LIBRARY_BINDING("library binding,bibliothekseinband", BookTypeClass.PRINTBOOK),
  BOARD_BOOK("board book,pappbilderbuch", BookTypeClass.PRINTBOOK),
  RAG_BOOK("rag book", BookTypeClass.PRINTBOOK),
  FLEXIBOUND("flexibound,flexibler einband,copertina flessibile,edizione economica", BookTypeClass.PRINTBOOK),
  LOOSE_LEAF("loose leaf,loseblattsammlung", BookTypeClass.PRINTBOOK),
  PAMPHLET("pamphlet,broschüre", BookTypeClass.PRINTBOOK),
  MAP("map,landkarte", BookTypeClass.PRINTBOOK);

  private final List<String> amazonLabels;
  private final BookTypeClass belongingClass;

  private BookType(String amazonLabelsString, BookTypeClass belongingClass) {
    String[] amazonLabels = amazonLabelsString.split(",");

    this.amazonLabels = Arrays.asList(amazonLabels);
    this.belongingClass = belongingClass;
  }

  public List<String> getAmazonLabels() {
    return this.amazonLabels;
  }

  public BookTypeClass getBelongingClass() {
    return this.belongingClass;
  }

  public static BookType findByAmazonLabel(String amazonLabel) {

    log.debug("Finding book format by amazon label {}.", amazonLabel);
    if (PAPERBACK.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return PAPERBACK;
    }
    if (HARDCOVER.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return HARDCOVER;
    }
    if (KINDLE.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return KINDLE;
    }
    if (AUDIO.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return AUDIO;
    }
    if (AUDIO_CD.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return AUDIO_CD;
    }
    if (SPIRAL_BOUND.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return SPIRAL_BOUND;
    }
    if (CALENDAR.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return CALENDAR;
    }
    if (SHEET_MUSIC.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return SHEET_MUSIC;
    }
    if (MAGAZINE.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return MAGAZINE;
    }
    if (SUPPLEMENT.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return SUPPLEMENT;
    }
    if (UNKNOWN_BINDING.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return UNKNOWN_BINDING;
    }
    if (LIBRARY_BINDING.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return LIBRARY_BINDING;
    }
    if (BOARD_BOOK.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return BOARD_BOOK;
    }
    if (RAG_BOOK.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return RAG_BOOK;
    }
    if (FLEXIBOUND.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return FLEXIBOUND;
    }
    if (LOOSE_LEAF.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return LOOSE_LEAF;
    }
    if (PAMPHLET.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return PAMPHLET;
    }
    if (MAP.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return MAP;
    }

    log.warn("Can't find supported book type {}.", amazonLabel);
    return null;

  }

}
