import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const SvpClientApp());
}

class SvpClientApp extends StatelessWidget {
  const SvpClientApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SVP Client',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
      ),
      home: const _SearchPage(),
    );
  }
}

class _SearchPage extends StatefulWidget {
  const _SearchPage({super.key});

  @override
  State<_SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends State<_SearchPage> {
  final TextEditingController _serviceController = TextEditingController();
  final TextEditingController _addressController = TextEditingController();
  final Dio _dio = Dio(
    BaseOptions(
      // Pour Android emulator, utiliser 10.0.2.2 au lieu de localhost.
      baseUrl: 'http://10.0.2.2:8080',
      connectTimeout: const Duration(seconds: 5),
      receiveTimeout: const Duration(seconds: 5),
    ),
  );

  bool _loading = false;
  final List<String> _results = <String>[];

  @override
  void dispose() {
    _serviceController.dispose();
    _addressController.dispose();
    super.dispose();
  }

  Future<void> _onLaunchSearch() async {
    final service = _serviceController.text.trim();
    if (service.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Merci de saisir un type de prestation')),
      );
      return;
    }

    setState(() {
      _loading = true;
      _results.clear();
    });

    try {
      // TODO: remplacer les coordonnées statiques par la vraie position (geolocator).
      final response = await _dio.post<Map<String, dynamic>>(
        '/api/demands',
        data: <String, dynamic>{
          'serviceType': service,
          'clientLatitude': 48.8566,
          'clientLongitude': 2.3522,
        },
      );

      final id = response.data?['id'];
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Demande envoyée (id=$id)')),
      );

      // Simulation d'arrivée progressive des artisans pendant 60s.
      _simulateResults(service);
    } on DioException catch (e) {
      final status = e.response?.statusCode;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            status != null
                ? 'Erreur serveur ($status), merci de réessayer.'
                : 'Erreur réseau, merci de vérifier votre connexion.',
          ),
        ),
      );
    } catch (_) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Erreur inattendue, merci de réessayer.')),
      );
    } finally {
      if (mounted) {
        setState(() {
          _loading = false;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('SVP Client'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              'Que puis-je faire pour vous ?',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _serviceController,
              decoration: const InputDecoration(
                labelText: 'Type de prestation (ex : Plombier, Fuite chauffe-eau)',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _addressController,
              decoration: const InputDecoration(
                labelText: 'Adresse (optionnel si GPS)',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 8),
            Align(
              alignment: Alignment.centerLeft,
              child: TextButton(
                onPressed: () {
                  // TODO: intégration geolocator pour récupérer la position GPS ponctuelle.
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                      content: Text('Récupération de la position (mock pour l’instant)'),
                    ),
                  );
                },
                child: const Text('Utiliser ma position actuelle'),
              ),
            ),
            const Spacer(),
            Expanded(
              child: ListView.builder(
                itemCount: _results.length,
                itemBuilder: (context, index) {
                  final item = _results[index];
                  return Card(
                    child: ListTile(
                      title: Text(item),
                    ),
                  );
                },
              ),
            ),
            const SizedBox(height: 8),
            FilledButton(
              onPressed: _loading ? null : _onLaunchSearch,
              child: _loading
                  ? const SizedBox(
                      height: 20,
                      width: 20,
                      child: CircularProgressIndicator(strokeWidth: 2),
                    )
                  : const Text('Lancer la recherche'),
            ),
          ],
        ),
      ),
    );
  }
}


