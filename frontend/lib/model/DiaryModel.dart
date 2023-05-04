class DiaryModel {
  DiaryModel({
    required this.diaryId,
    required this.memberId,
    this.characters,
    this.places,
    this.keyword,
    required this.prompt,
    required this.title,
    this.subtitles,
    required this.detail,
    required this.summary,
    required this.genre,
    required this.exchanged,
  });
  late final int diaryId;
  late final int memberId;
  late final List<String>? characters;
  late final List<String>? places;
  late final List<String>? keyword;
  late final String prompt;
  late final String title;
  late final List<String>? subtitles;
  late final String detail;
  late final String summary;
  late final List<String> genre;
  late final bool exchanged;

  DiaryModel.fromJson(Map<String, dynamic> json){
    diaryId = json['diaryId'];
    memberId = json['memberId'];
    characters = json['characters'] != null ? List<String>.from(json['characters'].map((dynamic x) => x as String)) : [];
    places = json['places'] != null ? List<String>.from(json['places'].map((dynamic x) => x as String)) : [];
    keyword = json['keyword'] != null ? List<String>.from(json['keyword'].map((dynamic x) => x as String)) : [];
    prompt = json['prompt'];
    title = json['title'];
    subtitles = json['subtitles'] != null ? List<String>.from(json['subtitles'].map((dynamic x) => x as String)) : [];
    detail = json['detail'];
    summary = json['summary'];
    genre = List.castFrom<dynamic, String>(json['genre']);
    exchanged = json['exchanged'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['diaryId'] = diaryId;
    _data['memberId'] = memberId;
    _data['characters'] = characters;
    _data['places'] = places;
    _data['keyword'] = keyword;
    _data['prompt'] = prompt;
    _data['title'] = title;
    _data['subtitles'] = subtitles;
    _data['detail'] = detail;
    _data['summary'] = summary;
    _data['genre'] = genre;
    _data['exchanged'] = exchanged;
    return _data;
  }
}