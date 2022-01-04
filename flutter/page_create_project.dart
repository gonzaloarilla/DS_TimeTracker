import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/page_intervals.dart';
import 'package:time_tracker_flutter/page_report.dart';
import 'package:time_tracker_flutter/tree.dart' hide getTree;
import 'package:time_tracker_flutter/requests.dart';
import 'package:time_tracker_flutter/page_search.dart';
import 'dart:core';
import 'dart:convert';

class PageCreateProject extends StatefulWidget {
  late final int id;
  // late TextEditingController projectController;
  late int parentId;
  PageCreateProject(this.parentId);

  @override
  _CreateProjectState createState() => _CreateProjectState();


}

class _CreateProjectState extends State<PageCreateProject> {
  GlobalKey formKey = GlobalKey<FormState>();
  late TextEditingController nameCtrl = TextEditingController();
  late TextEditingController tagsCtrl = TextEditingController();
  late TextEditingController descriptionCtrl = TextEditingController();
  late TextEditingController clearCtrl = TextEditingController();
  late TextEditingController searchCtrl = TextEditingController();
  late int parentId;

  @override
  void initState() {
    super.initState();
    // nameCtrl, tags = TextEditingController();
    parentId = widget.parentId;
  }

  @override
  void dispose() {
    nameCtrl.dispose();
    tagsCtrl.dispose();
    descriptionCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        // The search area here
          title: Container(
            width: MediaQuery.of(context).size.width,
            height: 40,
            alignment: Alignment.center,
            decoration: BoxDecoration(
                color: Colors.white, borderRadius: BorderRadius.circular(5)),
            child: Center(
              child: TextField(
                controller: searchCtrl,
                decoration: InputDecoration(
                    prefixIcon: IconButton(
                        icon: Icon(Icons.clear),
                        onPressed: () {
                          _onClearSearch(searchCtrl);
                        }
                    ),
                    suffixIcon: IconButton(
                      icon: Icon(Icons.search),
                      onPressed: () {
                        _onSearchTag(searchCtrl);
                      },
                    ),
                    hintText: 'Search a tag...',
                    border: InputBorder.none),
              ),
            ),
          )),
      body: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Form(
          key: formKey,
          child: Column(
            children: <Widget>[
              TextFormField(
                controller: nameCtrl,
                decoration: InputDecoration(
                    labelText: "Name: "
                ),
              ),
              TextFormField(
                controller: tagsCtrl,
                decoration: InputDecoration(
                    labelText: "Tags: "
                ),
              ),
              TextFormField(
                controller: descriptionCtrl,
                decoration: InputDecoration(
                    labelText: "Description: "
                ),
              ),

              OutlinedButton(
                child: Text('Create',
                  style: TextStyle(fontSize: 20),
                ),
                onPressed: (){
                  // _dialogForTest(tagsCtrl.text);
                  _makeEncoded(nameCtrl.text, descriptionCtrl.text, _splitTags(tagsCtrl.text));
                  int count = 0;
                  Navigator.of(context).popUntil((_) => count++ >= 2);

                },
                style: OutlinedButton.styleFrom(
                  side: BorderSide(
                    width: 2.0,
                    color: Colors.blue,
                    style: BorderStyle.solid,
                  ),

                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  // per testejar els resultats dels controladors
  void _dialogForTest(dynamic txt) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Controller Value'),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                Text(txt),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text(
                'ACCEPT',
                style: TextStyle(fontSize: 20),
              ),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  // per separar els tags
  List<String> _splitTags(String tags) {
    var splitted = tags.split(", ");
    return splitted;
  }

  void _makeEncoded(String name, String description, List<String> tags) {
    // dynamic --> pot ser qualsevol tipus
    Map<String, dynamic> toJson = {'name': name, 'description': description, 'tags': tags};
    // print(toJson);
    var jsoned = json.encode(toJson);
    print(jsoned);

  }

  void _onClearSearch(TextEditingController controller) {
    controller.text = "";

  }

  List<String> _onSearchTag(TextEditingController controller){
    var tagsToSearch = controller.text.split(", ");
    // _dialogForTest(tagsToSearch);

    print(tagsToSearch);

    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageSearch()));
    return tagsToSearch;
  }
}

